package ru.practicum.explorewithme.privaterequest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.mapper.ParticipationRequestMapper;
import ru.practicum.explorewithme.model.*;
import ru.practicum.explorewithme.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            throws ObjectNotFoundException, RequestErrorException {
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
       // if (!updateEventRequest.isPresent()) throw new RequestErrorException();
        Event event = eventValidation(updateEventRequest.getEventId());
        if (event.getInitiator().getId() == userId) throw new ConditionsOperationNotMetException();
        if (event.getRequestModeration() == false || event.getState() != State.CANCELED)
            throw new ConditionsOperationNotMetException();
        if (updateEventRequest.getEventDate() != null) {
            if (updateEventRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))
            throw new RequestErrorException();
            event.setEventDate(updateEventRequest.getEventDate());
        }
        if (event.getState() == State.CANCELED) event.setState(State.PENDING);
        if (!updateEventRequest.getAnnotation().isBlank()) event.setAnnotation(updateEventRequest.getAnnotation());
        if (updateEventRequest.getCategory() != null) {
            if (categoryRepository.findById(updateEventRequest.getCategory()).isPresent())
            event.setCategory(categoryRepository.findById(updateEventRequest.getCategory()).get());
        }
        if (updateEventRequest.getPaid() != null) event.setPaid(updateEventRequest.getPaid());
        if (updateEventRequest.getParticipantLimit() != null)
            event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        if (updateEventRequest.getTitle() != null) event.setTitle(updateEventRequest.getTitle());
        eventRepository.saveAndFlush(event);
        log.info("Изменения события добавленного текущим пользователем userId={}", userId);
        return EventMapper.toEventFullDto(event);
    }

    private Event eventValidation (Long eventId) throws ObjectNotFoundException, RequestErrorException {
       // if (!eventId.isPresent()) throw new RequestErrorException();
        Event event = eventRepository.findEventById(eventId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId)));
//        if (!event.isPresent())
//            throw new ObjectNotFoundException(String.format("Event with id={} was not found.", event));
        return event;
    }
    private User userValidation (Long userId) throws ObjectNotFoundException, RequestErrorException {
       // if (!userId.isPresent()) throw new RequestErrorException();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("User with id={} was not found.", userId)));
//        if (!user.isPresent())
//            throw new ObjectNotFoundException(String.format("User with id={} was not found.", userId.get()));
        return user;
    }

//    private ParticipationRequest participationRequestValidation (Long userId) throws MethodExceptions {
//        Optional<ParticipationRequest> participationRequest = requestRepository.findRequestUserById(userId);
//        if (!participationRequest.isPresent())
//            throw new MethodExceptions(String.format("ParticipationRequest with userId={} was not found.", userId),
//                    404, "The required object was not found.");
//        return participationRequest.get() ;
//    }
    @Override
    public EventFullDto createUserEvent(Long userId, NewEventDto newEventDto)
            throws ObjectNotFoundException, RequestErrorException {
        User user = userValidation(userId);
        //if (!newEventDto.isPresent()) throw new RequestErrorException();
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(
                  () -> new ObjectNotFoundException(String.format("Category with id={} was not found.", newEventDto.getCategory())));
        Event event = EventMapper.toEvent(newEventDto);
        event.setCategory(category);
        event.setInitiator(user);
        eventRepository.saveAndFlush(event);
        log.info("Добавление нового события eventId={}", event.getId());
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto getUserEventById(Long userId, Long eventId)
            throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException {
        userValidation(userId);
        Event event = eventValidation(eventId);
        if (event.getInitiator().getId() != userId) throw new ConditionsOperationNotMetException();
        log.info("Получение полной информации о событии полученной текущим пользователем userId={}", userId);
        return EventMapper.toEventFullDto(event);
    }
    @Override
    public EventFullDto patchCancelUserIdEvent(Long userId, Long eventId)
            throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException {
        userValidation(userId);
        Event event = eventValidation(eventId);
        if (event.getInitiator().getId() != userId)
            throw new ConditionsOperationNotMetException();
        if (!event.getRequestModeration())
            throw new ConditionsOperationNotMetException();
        log.info("Отмена события eventId={} добавленного текущим пользователем userId={}", eventId, userId);
        eventRepository.deleteById(event.getId());
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public ParticipationRequestDto getUserEventRequestsById(Long userId, Long eventId)
            throws ObjectNotFoundException, RequestErrorException {
        userValidation(userId);
        eventValidation(eventId);
        ParticipationRequest participationRequest =
                requestRepository.findRequestUserByIdAndEventById(userId, eventId).orElseThrow(
                        () -> new ObjectNotFoundException(String.format("ParticipationRequest with userId={}, eventId={} was not found.",
                                userId, eventId)));
//        if (!participationRequest.isPresent())
//            throw new ObjectNotFoundException(String.format("ParticipationRequest with userId={}, eventId={} was not found.",
//                    userId.get(), eventId.get()));
        log.info("Получение информации о запросах на участие в событии текущего пользователя userId={}", userId);
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);

    }

    @Override
    public ParticipationRequestDto patchUserRequestConfirm(Long userId, Long eventId, Long reqId)
            throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException {
        userValidation(userId);
        Event event = eventValidation(eventId);
        ParticipationRequest participationRequest = requestRepository.findById(reqId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("ParticipationRequest with reqId={} was not found.", reqId)));
//        if (!participationRequestOp.isPresent())
//            throw new ObjectNotFoundException(String.format("ParticipationRequest with reqId={} was not found.", reqId.get()));
//        ParticipationRequest participationRequest = participationRequestOp.get();
        if (event.getParticipantLimit() == 0 || event.getRequestModeration() == false)
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        if (event.getParticipantLimit().equals(event.getConfirmedRequests()))
            throw new ConditionsOperationNotMetException();
        if (!participationRequest.getRequester().getId().equals(userId) ||
                !participationRequest.getEvent().getId().equals(eventId))
            throw new ConditionsOperationNotMetException();
//        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        //eventRepository.saveAndFlush(event);
        participationRequest.setStatus(Status.CONFIRMED);
        requestRepository.saveAndFlush(participationRequest);
        log.info("Подтверждение чужой заявки на участие в событии текущего пользователя userId={}", userId);
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }

    @Override
    public ParticipationRequestDto patchUserRequestReject(Long userId, Long eventId, Long reqId)
            throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException {
        userValidation(userId);
        Event event = eventValidation(eventId);
        ParticipationRequest participationRequest = requestRepository.findById(reqId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("ParticipationRequest with reqId={} was not found.", reqId)));
//        if (!participationRequestOp.isPresent())
//            throw new ObjectNotFoundException(String.format("ParticipationRequest with reqId={} was not found.", reqId.get()));
//        ParticipationRequest participationRequest = participationRequestOp.get();
        if (event.getParticipantLimit() == 0 || event.getRequestModeration() == false)
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        if (!participationRequest.getRequester().getId().equals(userId) ||
                !participationRequest.getEvent().getId().equals(eventId))
            throw new ConditionsOperationNotMetException();
        participationRequest.setStatus(Status.REJECTED);
        requestRepository.saveAndFlush(participationRequest);
        log.info("Отклонение чужой заявки на участие в событии текущего пользователя userId={}", userId);
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }

}
