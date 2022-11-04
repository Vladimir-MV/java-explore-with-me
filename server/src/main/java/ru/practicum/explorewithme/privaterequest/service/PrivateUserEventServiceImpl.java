    package ru.practicum.explorewithme.privaterequest.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.*;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.EventMapper;
    import ru.practicum.explorewithme.mapper.ParticipationRequestMapper;
    import ru.practicum.explorewithme.model.*;
    import ru.practicum.explorewithme.repository.*;
    import java.time.LocalDateTime;
    import java.util.List;

    @Slf4j
    @Service
    public class PrivateUserEventServiceImpl implements PrivateUserEventService{
        private UserRepository userRepository;
        private CategoryRepository categoryRepository;
        private EventRepository eventRepository;
        private RequestRepository requestRepository;

        @Autowired
        public PrivateUserEventServiceImpl (EventRepository eventRepository, UserRepository userRepository,
            RequestRepository requestRepository, CategoryRepository categoryRepository) {
            this.eventRepository = eventRepository;
            this.userRepository = userRepository;
            this.categoryRepository = categoryRepository;
            this.requestRepository = requestRepository;
        }
        @Override
        public List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size)
                throws ObjectNotFoundException {
                User user = userValidation(userId);
                final Pageable pageable = FromSizeRequest.of(from, size);
                List<Event> listEvent = eventRepository.findEventsByUserId(user.getId(), pageable).getContent();
                if (listEvent.size() > 0) {
                    log.info("Получение событий добавленным текущим пользователем userId={}", userId);
                    return EventMapper.toListEventShortDto(listEvent);
                } else {
                    throw new ObjectNotFoundException(String.format("ListEvents with userId={} was not found.", userId));
                }
        }

        @Override
        public EventFullDto patchUserIdEvent(Long userId, UpdateEventRequest updateEventRequest)
                throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException {
            userValidation(userId);
            Event event = eventRepository.findUserEventById(userId, updateEventRequest.getEventId()).orElseThrow(
                    () -> new ObjectNotFoundException(
                        String.format("Event with id={} userId={} was not found.", updateEventRequest.getEventId(), userId)));
            if (event.getInitiator().getId() != userId) throw new ConditionsOperationNotMetException();
            if (event.getRequestModeration()) {
                event.setState(State.PENDING);
            } else if (event.getState() == State.CANCELED) {
                event.setState(State.PENDING);
                event.setRequestModeration(true);
            }
           if (updateEventRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) throw new RequestErrorException();
                event.setEventDate(updateEventRequest.getEventDate());
           if (updateEventRequest.getAnnotation() != null) event.setAnnotation(updateEventRequest.getAnnotation());
           if (updateEventRequest.getCategory() != null) {
                event.setCategory(categoryRepository.findById(updateEventRequest.getCategory()).orElseThrow(
                        () -> new ObjectNotFoundException(
                             String.format("Category with id={} was not found.", updateEventRequest.getCategory()))));
            }
            if (updateEventRequest.getDescription() != null) event.setDescription(updateEventRequest.getDescription());
            if (updateEventRequest.getPaid() != null) event.setPaid(updateEventRequest.getPaid());
            if (updateEventRequest.getParticipantLimit() != null)
                event.setParticipantLimit(updateEventRequest.getParticipantLimit());
            if (updateEventRequest.getTitle() != null) event.setTitle(updateEventRequest.getTitle());
            eventRepository.saveAndFlush(event);
            log.info("Изменения события добавленного текущим пользователем userId={}", userId);
            return EventMapper.toEventFullDto(event);
        }


        private Event eventValidation (Long eventId) throws ObjectNotFoundException {
            Event event = eventRepository.findEventById(eventId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId)));
            return event;
        }
        private User userValidation (Long userId) throws ObjectNotFoundException {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("User with id={} was not found.", userId)));
            return user;
        }

        @Override
        public EventFullDto createUserEvent(Long userId, NewEventDto newEventDto)
                throws ObjectNotFoundException {
            User user = userValidation(userId);
            Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(
                      () -> new ObjectNotFoundException(String.format("Category with id={} was not found.", newEventDto.getCategory())));
            Event event = EventMapper.toEvent(newEventDto);
            event.setCategory(category);
            event.setInitiator(user);
            eventRepository.save(event);
            log.info("Добавление нового события eventId={}", event.getId());
            return EventMapper.toEventFullDto(event);
        }

        @Override
        public EventFullDto getUserEventById(Long userId, Long eventId)
                throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException {
            userValidation(userId);
            Event event = eventRepository.findUserEventById(userId, eventId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Event with eventId={} and userId={} was not found.", eventId, userId)));
            log.info("Получение полной информации о событии полученной текущим пользователем userId={}", userId);
            return EventMapper.toEventFullDto(event);
        }
        @Override
        public EventFullDto patchCancelUserIdEvent(Long userId, Long eventId)
                throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException {
            userValidation(userId);
            Event event = eventRepository.findById(eventId).orElseThrow(
               () -> new ObjectNotFoundException(String.format("Event with eventId={} and userId={} was not found.", eventId, userId)));
            if (event.getInitiator().getId() != userId) throw new ConditionsOperationNotMetException();
            if (event.getState() != State.PENDING)
                throw new RequestErrorException();
            log.info("Отмена события eventId={} добавленного текущим пользователем userId={}", eventId, userId);
            event.setState(State.CANCELED);
            eventRepository.save(event);
            return EventMapper.toEventFullDto(event);
        }

        @Override
        public List<ParticipationRequestDto> getUserEventRequestsById(Long userId, Long eventId)
                throws ObjectNotFoundException, RequestErrorException {
            userValidation(userId);
            eventValidation(eventId);
            List<ParticipationRequest> participationRequest =
                    requestRepository.findRequestUserByIdAndEventById(eventId, userId).orElseThrow(
                            () -> new ObjectNotFoundException(String.format("ParticipationRequest with userId={}, eventId={} was not found.",
                                    userId, eventId)));
            log.info("Получение информации о запросах на участие в событии текущего пользователя userId={}", userId);
            return ParticipationRequestMapper.toListParticipationRequestDto(participationRequest);

        }

        @Override
        public ParticipationRequestDto patchUserRequestConfirm(Long userId, Long eventId, Long reqId)
                throws ObjectNotFoundException, ConditionsOperationNotMetException {
            userValidation(userId);
            Event event = eventValidation(eventId);
            ParticipationRequest participationRequest = requestRepository.findById(reqId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("ParticipationRequest with reqId={} was not found.", reqId)));
            if (event.getParticipantLimit() == 0 || !event.getRequestModeration())
                return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
            if (event.getParticipantLimit() == (event.getConfirmedRequests()))
                throw new ConditionsOperationNotMetException();
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
            participationRequest.setStatus(Status.CONFIRMED);
            requestRepository.saveAndFlush(participationRequest);
            log.info("Подтверждение чужой заявки на участие в событии текущего пользователя userId={}", userId);
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }

        @Override
        public ParticipationRequestDto patchUserRequestReject(Long userId, Long eventId, Long reqId)
                throws ObjectNotFoundException {
            userValidation(userId);
            eventValidation(eventId);
            ParticipationRequest participationRequest = requestRepository.findById(reqId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("ParticipationRequest with reqId={} was not found.", reqId)));
            participationRequest.setStatus(Status.REJECTED);
            requestRepository.saveAndFlush(participationRequest);
            log.info("Отклонение чужой заявки на участие в событии текущего пользователя userId={}", userId);
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }

    }
