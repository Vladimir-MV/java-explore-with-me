    package ru.practicum.explorewithme.admin.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.AdminUpdateEventRequest;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.EventMapper;
    import ru.practicum.explorewithme.model.*;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import ru.practicum.explorewithme.repository.UserRepository;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class AdminEventServiceImpl implements AdminEventService{
        private EventRepository eventRepository;
        private UserRepository userRepository;
        private CategoryRepository categoryRepository;

        @Autowired
        public AdminEventServiceImpl (EventRepository eventRepository,  UserRepository userRepository,
                                       CategoryRepository categoryRepository) {
            this.eventRepository = eventRepository;
            this.userRepository = userRepository;
            this.categoryRepository = categoryRepository;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        @Override
        public List<EventFullDto> getEventsByUsersStatesCategories(
                List<Long> users, List<String> states, List<Long> categories,
                String rangeStart, String rangeEnd, Integer from, Integer size) throws ObjectNotFoundException {
            List<State> listState = new ArrayList<>();
            for (String state: states) {
                listState.add(State.valueOf(state));
            }

            for (Long userId: users) {
                if (!userRepository.existsById(userId))
                    throw new ObjectNotFoundException(String.format("User with userId={} was not found.", userId));
            }

            for (Long catId: categories) {
                if (!categoryRepository.existsById(catId))
                    throw new ObjectNotFoundException(String.format("Category with categoryId={} was not found.", catId));
            }

            final Pageable pageable = FromSizeRequest.of(from, size);


            List <Event> listEvents = eventRepository.searchEventsByAdminGetConditions(users, listState, categories,
                        LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();

            if (!(listEvents.size() > 0))
                throw new ObjectNotFoundException(String.format("Event list with was not found."));
            return EventMapper.toListEventFullDto(listEvents);
        }

        private Event eventValidation (Long eventId) throws ObjectNotFoundException{
            Event event = eventRepository.findById(eventId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId)));
            return event;
        }

        @Override
        public EventFullDto putEventById(Long eventId,
            AdminUpdateEventRequest adminUpdateEventRequest) throws ObjectNotFoundException {
            Event event = eventRepository.findById(eventId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId)));
            if (adminUpdateEventRequest.getAnnotation() != null)
                        event.setAnnotation(adminUpdateEventRequest.getAnnotation());
            if (adminUpdateEventRequest.getCategory() != null)
               event.setCategory(categoryRepository.findById(adminUpdateEventRequest.getCategory()).orElseThrow(
                  () -> new ObjectNotFoundException(
                      String.format("Category id={} was not found.", adminUpdateEventRequest.getCategory()))));
            if (adminUpdateEventRequest.getDescription() != null)
                event.setDescription(adminUpdateEventRequest.getDescription());
            if (adminUpdateEventRequest.getEventDate() != null)
                event.setEventDate(adminUpdateEventRequest.getEventDate());
            if (adminUpdateEventRequest.getLocation() != null)
                event.setLocation(adminUpdateEventRequest.getLocation());
            if (adminUpdateEventRequest.getPaid() != null)
                event.setPaid(adminUpdateEventRequest.getPaid());
            if (adminUpdateEventRequest.getParticipantLimit() != null)
                event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
            if (adminUpdateEventRequest.getRequestModeration() != null)
                event.setRequestModeration(adminUpdateEventRequest.getRequestModeration());
            if (adminUpdateEventRequest.getTitle() != null)
                event.setTitle(adminUpdateEventRequest.getTitle());
            eventRepository.saveAndFlush(event);
            log.info("Поиск события eventId={}", event.getId());
            return EventMapper.toEventFullDto(event);
        }

        @Override
        public EventFullDto patchPublishEventById(Long eventId)
                throws ObjectNotFoundException, ConditionsOperationNotMetException {
            Event event = eventValidation(eventId);
            if (event.getState() != State.PENDING) throw new ConditionsOperationNotMetException();
            event.setPublishedOn(LocalDateTime.now());
            if (event.getEventDate().isBefore(event.getPublishedOn().plusHours(1)))
                throw new ConditionsOperationNotMetException();
            event.setState(State.PUBLISHED);
            eventRepository.saveAndFlush(event);
            log.info("Публикация события eventId={}", event.getId());
            return EventMapper.toEventFullDto(event);
        }

        @Override
        public EventFullDto patchRejectEventById(Long eventId)
                throws ObjectNotFoundException, ConditionsOperationNotMetException {
            Event event = eventValidation(eventId);
            if (event.getState() == State.PUBLISHED || event.getState() == State.CANCELED)
                throw new ConditionsOperationNotMetException();
            event.setState(State.CANCELED);
            eventRepository.saveAndFlush(event);
            log.info("Отклонение события userId={}", eventId);
            return EventMapper.toEventFullDto(event);
        }
    }
