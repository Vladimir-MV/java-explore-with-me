package ru.practicum.explorewithme.privaterequest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
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
    public List<EventShortDto> getUserEvents(Optional<Long> userId, Integer from, Integer size) throws MethodExceptions {
            User user = userValidation(userId);
            final Pageable pageable = FromSizeRequest.of(from, size);
            List<Event> listEvent = eventRepository.findEventsByUserId(user.getId(), pageable).getContent();
            if (!listEvent.isEmpty()) {
                log.info("Получение событий добавленным текущим пользователем userId={}", userId.get());
                return EventMapper.toListEventShortDto(listEvent);
            } else {
                throw new MethodExceptions(String.format("ListEvents with userId={} was not found.", userId.get()),
                        404, "The required object was not found.");
            }
    }

    @Override
    public EventFullDto patchUserIdEvent(Optional<Long> userId, Optional<UpdateEventRequest> updateEventRequest) throws MethodExceptions {
        User user = userValidation(userId);
        if (!updateEventRequest.isPresent()) throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                400, "For the requested operation the conditions are not met.");
        Event event = eventValidation(Optional.of(updateEventRequest.get().getEventId()));
        if (event.getInitiator().getId() != userId.get()) throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                403, "For the requested operation the conditions are not met.");
        if (event.getRequestModeration() == false || !event.getState().equals(State.CANCELED))
            throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                403, "For the requested operation the conditions are not met.");
        if (updateEventRequest.get().getEventDate() != null) {
            if (updateEventRequest.get().getEventDate().isBefore(LocalDateTime.now().plusHours(2)))
            throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                    400, "For the requested operation the conditions are not met.");
            event.setEventDate(updateEventRequest.get().getEventDate());
        }
        if (event.getState().equals(State.CANCELED)) event.setState(State.PENDING);
        if (!updateEventRequest.get().getAnnotation().isBlank()) event.setAnnotation(updateEventRequest.get().getAnnotation());
        if (updateEventRequest.get().getCategory() != null) {
            if (categoryRepository.findCategoryById(updateEventRequest.get().getCategory()).isPresent())
            event.setCategory(categoryRepository.findCategoryById(updateEventRequest.get().getCategory()).get());
        }
        if (updateEventRequest.get().getPaid() != null) event.setPaid(updateEventRequest.get().getPaid());
        if (updateEventRequest.get().getParticipantLimit() != null)
            event.setParticipantLimit(updateEventRequest.get().getParticipantLimit());
        if (updateEventRequest.get().getTitle() != null) event.setTitle(updateEventRequest.get().getTitle());
        eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Изменения события добавленного текущим пользователем userId={}", userId.get());
        return eventFullDto;
    }

    private Event eventValidation (Optional<Long> eventId) throws MethodExceptions {
        if (!eventId.isPresent()) throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                400, "For the requested operation the conditions are not met.");
        Optional<Event> event = eventRepository.findEventsById(eventId.get());
        if (!event.isPresent())
            throw new MethodExceptions(String.format("Event with id={} was not found.", event.get()),
                    404, "The required object was not found.");
        return event.get();
    }
    private User userValidation (Optional<Long> userId) throws MethodExceptions {
        if (!userId.isPresent()) throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                400, "For the requested operation the conditions are not met.");
        Optional<User> user = userRepository.findUserById(userId.get());
        if (!user.isPresent())
            throw new MethodExceptions(String.format("User with id={} was not found.", userId.get()),
                    404, "The required object was not found.");
        return user.get() ;
    }

//    private ParticipationRequest participationRequestValidation (Long userId) throws MethodExceptions {
//        Optional<ParticipationRequest> participationRequest = requestRepository.findRequestUserById(userId);
//        if (!participationRequest.isPresent())
//            throw new MethodExceptions(String.format("ParticipationRequest with userId={} was not found.", userId),
//                    404, "The required object was not found.");
//        return participationRequest.get() ;
//    }
    @Override
    public EventFullDto createUserEvent(Optional<Long> userId, Optional<NewEventDto> newEventDto) throws MethodExceptions {
        User user = userValidation(userId);
        if (newEventDto.isPresent()) {
            Category category = categoryRepository.findCategoryById(newEventDto.get().getCategory()).get();
            Event event = EventMapper.toEvent(newEventDto.get());
            event.setCategory(category);
            event.setInitiator(user);
            log.info("Добавление нового события userId={}", userId.get());
            eventRepository.save(event);
        }
            throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                    400, "For the requested operation the conditions are not met.");
    }

    @Override
    public EventFullDto getUserEventById(Optional<Long> userId, Optional<Long> eventId) throws MethodExceptions {
        userValidation(userId);
        Event event = eventValidation(eventId);
        if (event.getInitiator().getId() != userId.get()) throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                403, "For the requested operation the conditions are not met.");
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Получение полной информации о событии полученной текущим пользователем userId={}", userId.get());
        return eventFullDto;
    }
    @Override
    public EventFullDto patchCancelUserIdEvent(Optional<Long> userId, Optional<Long> eventId) throws MethodExceptions {
        userValidation(userId);
        Event event = eventValidation(eventId);
        if (event.getInitiator().getId() != userId.get())
            throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                403, "For the requested operation the conditions are not met.");
        if (!event.getRequestModeration())
            throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                403, "For the requested operation the conditions are not met.");
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Отмена события eventId={} добавленного текущим пользователем userId={}", eventId.get(), userId.get());
        eventRepository.delete(event);
        return eventFullDto;
    }

    @Override
    public ParticipationRequestDto getUserEventRequestsById(Optional<Long> userId, Optional<Long> eventId) throws MethodExceptions {
        userValidation(userId);
        eventValidation(eventId);
        Optional<ParticipationRequest> participationRequest =
                requestRepository.findRequestUserByIdAndEventById(userId.get(), eventId.get());
        if (!participationRequest.isPresent())
            throw new MethodExceptions(String.format("ParticipationRequest with userId={}, eventId={} was not found.",
                    userId.get(), eventId.get()), 404, "The required object was not found.");
        log.info("Получение информации о запросах на участие в событии текущего пользователя userId={}", userId.get());
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest.get());

    }

    @Override
    public ParticipationRequestDto patchUserRequestConfirm(Optional<Long> userId, Optional<Long> eventId, Optional<Long> reqId) throws MethodExceptions {
        userValidation(userId);
        Event event = eventValidation(eventId);
        Optional<ParticipationRequest> participationRequestOp = requestRepository.findRequestById(reqId.get());
        if (!participationRequestOp.isPresent())
            throw new MethodExceptions(String.format("ParticipationRequest with reqId={} was not found.", reqId.get()),
                    404, "The required object was not found.");
        ParticipationRequest participationRequest = participationRequestOp.get();
        if (event.getParticipantLimit() == 0 || event.getRequestModeration() == false)
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        if (event.getParticipantLimit().equals(event.getConfirmedRequests()))
            throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                    403, "For the requested operation the conditions are not met.");
        if (!participationRequest.getRequester().getId().equals(userId.get()) ||
                !participationRequest.getEvent().getId().equals(eventId.get()))
            throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                    403, "For the requested operation the conditions are not met.");
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        eventRepository.save(event);
        participationRequest.setStatus("CONFIRMED");
        requestRepository.save(participationRequest);
        log.info("Подтверждение чужой заявки на участие в событии текущего пользователя userId={}", userId.get());
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }

    @Override
    public ParticipationRequestDto patchUserRequestReject(Optional<Long> userId, Optional<Long> eventId, Optional<Long> reqId) throws MethodExceptions {
        userValidation(userId);
        Event event = eventValidation(eventId);
        Optional<ParticipationRequest> participationRequestOp = requestRepository.findRequestById(reqId.get());
        if (!participationRequestOp.isPresent())
            throw new MethodExceptions(String.format("ParticipationRequest with reqId={} was not found.", reqId.get()),
                    404, "The required object was not found.");
        ParticipationRequest participationRequest = participationRequestOp.get();
        if (event.getParticipantLimit() == 0 || event.getRequestModeration() == false)
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        if (!participationRequest.getRequester().getId().equals(userId.get()) ||
                !participationRequest.getEvent().getId().equals(eventId.get()))
            throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                    403, "For the requested operation the conditions are not met.");
        participationRequest.setStatus("REJECTED");
        requestRepository.save(participationRequest);
        log.info("Отклонение чужой заявки на участие в событии текущего пользователя userId={}", userId.get());
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }

}
