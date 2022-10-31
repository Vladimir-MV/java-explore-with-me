    package ru.practicum.explorewithme.privaterequest.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.NewEventDto;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.model.UpdateEventRequest;
    import ru.practicum.explorewithme.privaterequest.service.PrivateUserEventService;
    import ru.practicum.explorewithme.privaterequest.service.PrivateUserEventServiceImpl;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/users/{userId}/events")
    @Slf4j
    public class PrivateUserEventController {
        PrivateUserEventService privateUserEventService;

        @Autowired
        public PrivateUserEventController (PrivateUserEventServiceImpl privateUserEventServiceImpl) {
            this.privateUserEventService = privateUserEventServiceImpl;
        }

        @GetMapping()
        public List<EventShortDto> privateUserEvents(
             @PathVariable Optional<Long> userId,
             @RequestParam(name = "from", defaultValue = "0") Integer from,
             @RequestParam(name = "size", defaultValue = "10") Integer size) throws ObjectNotFoundException, RequestErrorException {
            log.info("privateUserEvents, get categories with userId={}, from={}, size={}", userId, from, size);
            return privateUserEventService.getUserEvents(userId, from, size);
        }

        @PatchMapping()
        public EventFullDto privatePatchUserEvent(
            @PathVariable Optional<Long> userId,
            @RequestBody Optional<UpdateEventRequest> updateEventRequest) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("patchUserEvent, patch user event with userId={}, updateEventRequest {}", userId, updateEventRequest);
            return privateUserEventService.patchUserIdEvent(userId, updateEventRequest);
        }

        @PostMapping()
        public EventFullDto privateCreateEvent(
            @PathVariable Optional<Long> userId,
            @RequestBody Optional<NewEventDto> newEventDto) throws ObjectNotFoundException, RequestErrorException {
            log.info("createEvent, create event with userId={}, newEventDto {}", userId, newEventDto);
            return privateUserEventService.createUserEvent(userId, newEventDto);
        }

        @GetMapping("/{eventId}")
        public EventFullDto privateUserEventById(
            @PathVariable Optional<Long> userId,
            @PathVariable Optional<Long> eventId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privateUserEventById get user event by userId={}, eventId={}", userId, eventId);
            return privateUserEventService.getUserEventById(userId, eventId);
        }

        @PatchMapping("/{eventId}")
        public EventFullDto privatePatchСancelEvent(
            @PathVariable Optional<Long> userId,
            @PathVariable Optional<Long> eventId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("patchСancelEvent, сancel event with userId={}, eventId={}", userId, eventId);
            return privateUserEventService.patchCancelUserIdEvent(userId, eventId);
        }

        @GetMapping("/{eventId}/requests")
        public ParticipationRequestDto privateUserEventRequests(
            @PathVariable Optional<Long> userId,
            @PathVariable Optional<Long> eventId) throws ObjectNotFoundException, RequestErrorException {
            log.info("privateUserEventRequests get requests user event by userId={}, eventId={}", userId, eventId);
            return privateUserEventService.getUserEventRequestsById(userId, eventId);
        }
        @PatchMapping("/{eventId}/requests/{reqId}/confirm")
        public ParticipationRequestDto privatePatchRequestConfirm(
            @PathVariable Optional<Long> userId,
            @PathVariable Optional<Long> eventId,
            @PathVariable Optional<Long> reqId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privatePatchRequestConfirm, confirm request userId={}, eventId={}, reqId={}", userId, eventId, reqId);
            return privateUserEventService.patchUserRequestConfirm(userId, eventId, reqId);
        }

        @PatchMapping("/{eventId}/requests/{reqId}/reject")
        public ParticipationRequestDto privatePatchRequestReject(
                @PathVariable Optional<Long> userId,
                @PathVariable Optional<Long> eventId,
                @PathVariable Optional<Long> reqId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privatePatchRequestReject, confirm request userId={}, eventId={}, reqId={}", userId, eventId, reqId);
            return privateUserEventService.patchUserRequestReject(userId, eventId, reqId);
        }

    }