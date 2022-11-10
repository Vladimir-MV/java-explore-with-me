    package ru.practicum.explorewithme.admin.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
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
    public class AdminEventServiceImpl implements AdminEventService {
        private final EventRepository eventRepository;
        private final UserRepository userRepository;
        private final CategoryRepository categoryRepository;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Transactional(readOnly = true)
        @Override
        public List<EventFullDto> getEventsByUsersStatesCategories(
            List<Long> users, List<String> states, List<Long> categories,
            String rangeStart, String rangeEnd, Integer from, Integer size) throws ObjectNotFoundException {
            List<State> listState = new ArrayList<>();
            List <Event> listEvents;
            if (states != null) {
                for (String state: states) {
                    listState.add(State.valueOf(state));
                }
            }

            for (Long userId: users) {
                if (!userRepository.existsById(userId))
                    throw new ObjectNotFoundException("Объект не найден. ",
                            String.format("User with userId={} was not found.", userId));
            }

            for (Long catId: categories) {
                if (!categoryRepository.existsById(catId))
                    throw new ObjectNotFoundException("Объект не найден. ",
                            String.format("Category with categoryId={} was not found.", catId));
            }

            final Pageable pageable = FromSizeRequest.of(from, size);

            if (states == null && rangeStart == null && rangeEnd == null) {
                listEvents = eventRepository
                        .searchEventsByAdminWithOutStatesAndRange(users, categories, pageable).getContent();
            } else {
                listEvents = eventRepository
                        .searchEventsByAdminGetConditions(
                                users, listState, categories,
                                LocalDateTime.parse(rangeStart, formatter),
                                LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }

           if (listEvents.isEmpty()) {
               throw new ObjectNotFoundException("Объект не найден. ",
                       String.format("Event list with was not found."));
           }
            return EventMapper.toListEventFullDto(listEvents);
        }

        private Event eventValidation (Long eventId) throws ObjectNotFoundException {
            return eventRepository.findById(eventId).orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("Event with id={} was not found.", eventId)));
        }

        @Transactional
        @Override
        public EventFullDto putEventById(Long eventId,
            AdminUpdateEventRequest adminUpdateEventRequest) throws ObjectNotFoundException {
            Event event = eventValidation(eventId);
            if (adminUpdateEventRequest.getAnnotation() != null) {
                event.setAnnotation(adminUpdateEventRequest.getAnnotation());
            }
            if (adminUpdateEventRequest.getCategory() != null) {
                event.setCategory(categoryRepository.findById(adminUpdateEventRequest.getCategory())
                        .orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                                String.format("Category id={} was not found.", adminUpdateEventRequest.getCategory()))));
            }
            if (adminUpdateEventRequest.getDescription() != null) {
                event.setDescription(adminUpdateEventRequest.getDescription());
            }
            if (adminUpdateEventRequest.getEventDate() != null) {
                event.setEventDate(adminUpdateEventRequest.getEventDate());
            }
            if (adminUpdateEventRequest.getLocation() != null) {
                event.setLocation(adminUpdateEventRequest.getLocation());
            }
            if (adminUpdateEventRequest.getPaid() != null) {
                event.setPaid(adminUpdateEventRequest.getPaid());
            }
            if (adminUpdateEventRequest.getParticipantLimit() != null) {
                event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
            }
            if (adminUpdateEventRequest.getRequestModeration() != null) {
                event.setRequestModeration(adminUpdateEventRequest.getRequestModeration());
            }
            if (adminUpdateEventRequest.getTitle() != null) {
                event.setTitle(adminUpdateEventRequest.getTitle());
            }
            eventRepository.saveAndFlush(event);
            log.info("Поиск события eventId={}", event.getId());
            return EventMapper.toEventFullDto(event);
        }

        @Transactional
        @Override
        public EventFullDto patchPublishEventById(Long eventId)
                throws ObjectNotFoundException, ConditionsOperationNotMetException {
            Event event = eventValidation(eventId);
            if (event.getState() != State.PENDING) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для" +
                        " совершения операции", "State");
            }
            event.setPublishedOn(LocalDateTime.now());
            if (event.getEventDate().isBefore(event.getPublishedOn().plusHours(1))) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для" +
                        " совершения операции", "EventDate");
            }
            event.setState(State.PUBLISHED);
            eventRepository.saveAndFlush(event);
            log.info("Публикация события eventId={}", event.getId());
            return EventMapper.toEventFullDto(event);
        }

        @Transactional
        @Override
        public EventFullDto patchRejectEventById(Long eventId)
                throws ObjectNotFoundException, ConditionsOperationNotMetException {
            Event event = eventValidation(eventId);
            if (event.getState() != State.PENDING) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для совершения операции", "State");
            }
            event.setState(State.CANCELED);
            eventRepository.saveAndFlush(event);
            log.info("Отклонение события userId={}", eventId);
            return EventMapper.toEventFullDto(event);
        }
    }
