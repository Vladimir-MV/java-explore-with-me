    package ru.practicum.explorewithme.admin.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.EventMapper;
    import ru.practicum.explorewithme.model.*;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import ru.practicum.explorewithme.repository.UserRepository;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.List;
    import java.util.Optional;
    @Slf4j
    @Service
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
        public List<EventFullDto> getEventsByUsersStatesCategories(List<Long> users, List<String> states, List<Long> categories,
                                                                   String rangeStart, String rangeEnd, Integer from, Integer size) throws ObjectNotFoundException {
            final Pageable pageable = FromSizeRequest.of(from, size);
            List <Event> listEvents = eventRepository.searchEventsByAdminGetConditions(users, states, categories,
                    LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            if (listEvents.isEmpty())
                throw new ObjectNotFoundException(String.format("Event list with was not found."));
            return EventMapper.toListEventFullDto(listEvents);

        }
        private Event eventValidation (Optional<Long> eventId) throws ObjectNotFoundException, RequestErrorException {
            if (!eventId.isPresent()) throw new RequestErrorException();
            Optional<Event> event = eventRepository.findEventById(eventId.get());
            if (!event.isPresent())
                throw new ObjectNotFoundException(String.format("Event with id={} was not found.", event.get()));
            return event.get();
        }

        @Override
        public EventFullDto putEventById(Long eventId,
            AdminUpdateEventRequest adminUpdateEventRequest) throws ObjectNotFoundException {
            Event event = eventRepository.findEventById(eventId).get();
            System.out.println(event);
            if (event == null)
                throw new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId));
            event.setAnnotation(adminUpdateEventRequest.getAnnotation());
            event.setCategory(categoryRepository.findCategoryById(adminUpdateEventRequest.getCategory()).get());
            event.setEventDate(LocalDateTime.parse(adminUpdateEventRequest.getEventDate()));
            event.setLocation(adminUpdateEventRequest.getLocation());
            event.setPaid(adminUpdateEventRequest.getPaid());
            event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
            event.setTitle(adminUpdateEventRequest.getTitle());
            eventRepository.save(event);
            log.info("Поиск события eventId={}", eventId);
            return EventMapper.toEventFullDto(event);
        }

        @Override
        public EventFullDto patchPublishEventById(Optional<Long> eventId) throws ObjectNotFoundException,
                RequestErrorException, ConditionsOperationNotMetException {
            Event event = eventValidation(eventId);
//            if (event == null)
//                throw new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId));
            if (!event.getState().equals(State.PENDING)) throw new ConditionsOperationNotMetException();
            if (!event.getEventDate().isAfter(event.getPublishedOn().plusHours(1)))
                throw new ConditionsOperationNotMetException();
            event.setState(State.PUBLISHED);
            eventRepository.save(event);
            log.info("Публикация события userId={}", eventId.get());
            return EventMapper.toEventFullDto(event);
        }

        @Override
        public EventFullDto patchRejectEventById(Optional<Long> eventId) throws ObjectNotFoundException,
                RequestErrorException, ConditionsOperationNotMetException {
            Event event = eventValidation(eventId);
            if (event == null)
                throw new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId));
            if (event.getState().equals(State.PUBLISHED) || event.getState().equals(State.CANCELED))
                throw new ConditionsOperationNotMetException();
            event.setState(State.CANCELED);
            eventRepository.save(event);
            log.info("Отклонение события userId={}", eventId.get());
            return EventMapper.toEventFullDto(event);
        }
    }
