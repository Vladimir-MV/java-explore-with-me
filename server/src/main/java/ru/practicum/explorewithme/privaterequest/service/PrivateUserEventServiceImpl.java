    package ru.practicum.explorewithme.privaterequest.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.dto.*;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.EventMapper;
    import ru.practicum.explorewithme.mapper.ParticipationRequestMapper;
    import ru.practicum.explorewithme.model.*;
    import ru.practicum.explorewithme.repository.*;
    import java.time.LocalDateTime;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PrivateUserEventServiceImpl implements PrivateUserEventService {

        private final UserRepository userRepository;
        private final CategoryRepository categoryRepository;
        private final EventRepository eventRepository;
        private final RequestRepository requestRepository;
        private final LocationGroupRepository locationGroupRepository;
        private final LocationRepository locationRepository;

        @Transactional(readOnly = true)
        @Override
        public List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size)
                                        throws ObjectNotFoundException {
                final Pageable pageable = FromSizeRequest.of(from, size);
                List<Event> listEvent = eventRepository.findEventsByUserId(userId, pageable).getContent();
                if (listEvent.isEmpty()) {
                    throw new ObjectNotFoundException("Объект не найден. ",
                            String.format("ListEvents with userId={} was not found.", userId));
                }
                log.info("Получение событий добавленным текущим пользователем userId={}", userId);
                return EventMapper.toListEventShortDto(listEvent);
        }

        @Transactional
        @Override
        public EventFullDto patchUserIdEvent(Long userId, UpdateEventRequest updateEventRequest)
                throws ObjectNotFoundException,
                       RequestErrorException,
                       ConditionsOperationNotMetException {
            userValidation(userId);
            Event event = eventRepository
                    .findUserEventById(userId, updateEventRequest.getEventId())
                    .orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                        String.format("Event with id={} userId={} was not found.",
                        updateEventRequest.getEventId(), userId)));
            if (!event.getInitiator().getId().equals(userId)) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для совершения операции", "Initiator");
            }
            if (event.getState().equals(State.PUBLISHED)) {
                throw new RequestErrorException("Запрос составлен с ошибкой. ", "State");
            }
            if (event.getState().equals(State.CANCELED)) {
                event.setRequestModeration(true);
            }
           if (updateEventRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
               throw new RequestErrorException("Запрос составлен с ошибкой. ", "EventRequest");
           }
                event.setEventDate(updateEventRequest.getEventDate());
           if (updateEventRequest.getAnnotation() != null) {
               event.setAnnotation(updateEventRequest.getAnnotation());
           }
           if (updateEventRequest.getCategory() != null) {
                event.setCategory(categoryRepository
                        .findById(updateEventRequest.getCategory()).orElseThrow(
                        () -> new ObjectNotFoundException("Объект не найден. ",
                             String.format("Category with id={} was not found.",
                             updateEventRequest.getCategory()))));
            }
            if (updateEventRequest.getDescription() != null) {
                event.setDescription(updateEventRequest.getDescription());
            }
            if (updateEventRequest.getPaid() != null) {
                event.setPaid(updateEventRequest.getPaid());
            }
            if (updateEventRequest.getParticipantLimit() != null) {
                event.setParticipantLimit(updateEventRequest.getParticipantLimit());
            }
            if (updateEventRequest.getTitle() != null) {
                event.setTitle(updateEventRequest.getTitle());
            }
            event.setState(State.PENDING);
            eventRepository.saveAndFlush(event);
            log.info("Изменения события добавленного текущим пользователем userId={}", userId);
            return EventMapper.toEventFullDto(event);
        }

        private Event eventValidation(Long eventId) throws ObjectNotFoundException {
            return eventRepository.findById(eventId).orElseThrow(() ->
                            new ObjectNotFoundException("Объект не найден. ",
                            String.format("Event with id={} was not found.", eventId)));
        }

        private User userValidation(Long userId) throws ObjectNotFoundException {
            return userRepository.findById(userId).orElseThrow(() ->
                            new ObjectNotFoundException("Объект не найден. ",
                            String.format("User with id={} was not found.", userId)));
        }

        @Transactional
        @Override
        public EventFullDto createUserEvent(Long userId, NewEventDto newEventDto)
                throws ObjectNotFoundException, RequestErrorException {
            User user = userValidation(userId);
            if (locationRepository.findByLatAndLon(newEventDto.getLocation().getLat(),
                                                   newEventDto.getLocation().getLon()).isPresent()) {
                throw new RequestErrorException("Запрос составлен с ошибкой. ", "locationRepository");
            }
            if (!newEventDto.getEventDate().minusHours(2).isAfter(LocalDateTime.now())) {
                throw new RequestErrorException("Запрос составлен с ошибкой. ", "EventDate");
            }
            Category category = categoryRepository
                    .findById(newEventDto.getCategory()).orElseThrow(() ->
                              new ObjectNotFoundException("Объект не найден. ",
                              String.format("Category with id={} was not found.",
                              newEventDto.getCategory())));
            Event event = EventMapper.toEvent(newEventDto);
            //Фича: Проверка принадлежит событие заданным локациям или нет.
            List<LocationGroup> locationGroupList = locationGroupRepository.findAll();
            if (!locationGroupList.isEmpty()) {
                Set<LocationGroup> locationGroupSet = new HashSet<>();
                for (LocationGroup locationGroup : locationGroupList) {
                    if (locationGroup.getRadius() >= locationGroupRepository
                            .distanceBetweenLocations(locationGroup.getLat(), locationGroup.getLon(),
                            newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon())) {
                        locationGroupSet.add(locationGroup);
                    }
                }
                if (locationGroupSet.isEmpty()) {
                    throw new RequestErrorException("Запрос составлен с ошибкой. ", "locationGroup");
                }
            event.setLocationGroup(locationGroupSet);
            }
            //
            event.setCategory(category);
            event.setInitiator(user);
            locationRepository.save(newEventDto.getLocation());
            eventRepository.save(event);
            log.info("Добавление нового события eventId={}", event.getId());
            return EventMapper.toEventFullDto(event);
        }

        @Transactional(readOnly = true)
        @Override
        public EventFullDto getUserEventById(Long userId, Long eventId)
                throws ObjectNotFoundException {
            userValidation(userId);
            Event event = eventRepository
                    .findUserEventById(userId, eventId).orElseThrow(() ->
                            new ObjectNotFoundException("Объект не найден. ",
                            String.format("Event with eventId={} and userId={} was not found.",
                            eventId, userId)));
            log.info("Получение полной информации о событии полученной текущим пользователем userId={}", userId);
            return EventMapper.toEventFullDto(event);
        }

        @Transactional
        @Override
        public EventFullDto patchCancelUserIdEvent(Long userId, Long eventId)
                throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException {
            userValidation(userId);
            Event event = eventRepository
                    .findById(eventId).orElseThrow(() ->
                                 new ObjectNotFoundException("Объект не найден. ",
                                 String.format("Event with eventId={} and userId={} was not found.",
                                 eventId, userId)));
            if (!event.getInitiator().getId().equals(userId)) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для совершения операции", "Initiator");
            }
            if (!event.getState().equals(State.PENDING)) {
                throw new RequestErrorException("Запрос составлен с ошибкой. ", "State");
            }
            log.info("Отмена события eventId={} добавленного текущим пользователем userId={}", eventId, userId);
            event.setState(State.CANCELED);
            eventRepository.save(event);
            return EventMapper.toEventFullDto(event);
        }

        @Transactional(readOnly = true)
        @Override
        public List<ParticipationRequestDto> getUserEventRequestsById(Long userId, Long eventId)
                throws ObjectNotFoundException {
            List<ParticipationRequest> participationRequest =
                    requestRepository.findRequestUserByIdAndEventById(eventId, userId)
                               .orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                               String.format("ParticipationRequest with userId={}, eventId={} was not found.",
                               userId, eventId)));
            log.info("Получение информации о запросах на участие в событии текущего пользователя userId={}", userId);
            return ParticipationRequestMapper.toListParticipationRequestDto(participationRequest);
        }

        @Transactional
        @Override
        public ParticipationRequestDto patchUserRequestConfirm(Long userId, Long eventId, Long reqId)
                throws ObjectNotFoundException, ConditionsOperationNotMetException {
            userValidation(userId);
            Event event = eventValidation(eventId);
            if (!event.getInitiator().getId().equals(userId)) {
                throw new RequestErrorException("Запрос составлен с ошибкой", "неверно задан userId");
            }
            ParticipationRequest participationRequest = requestRepository
                    .findById(reqId).orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("ParticipationRequest with reqId={} was not found.", reqId)));
            if (!participationRequest.getEvent().getId().equals(eventId)) {
                throw new RequestErrorException("Запрос составлен с ошибкой", "неверно задан eventId");
            }
            if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
                return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
            }
            if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для совершения операции", "ParticipantLimit");
            }

            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
            participationRequest.setStatus(Status.CONFIRMED);
            requestRepository.save(participationRequest);
            log.info("Подтверждение чужой заявки на участие в событии текущего пользователя userId={}", userId);
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }

        @Transactional
        @Override
        public ParticipationRequestDto patchUserRequestReject(Long userId, Long eventId, Long reqId)
                throws ObjectNotFoundException {
            userValidation(userId);
            if (!eventValidation(eventId).getInitiator().getId().equals(userId)) {
                throw new RequestErrorException("Запрос составлен с ошибкой", "неверно задан userId");
            }
            ParticipationRequest participationRequest = requestRepository
                    .findById(reqId).orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("ParticipationRequest with reqId={} was not found.", reqId)));
            if (!participationRequest.getEvent().getId().equals(eventId)) {
                throw new RequestErrorException("Запрос составлен с ошибкой", "неверно задан eventId");
            }

            participationRequest.setStatus(Status.REJECTED);
            requestRepository.save(participationRequest);
            log.info("Отклонение чужой заявки на участие в событии текущего пользователя userId={}", userId);
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }
    }
