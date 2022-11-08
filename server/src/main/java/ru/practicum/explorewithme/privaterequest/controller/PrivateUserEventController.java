    package ru.practicum.explorewithme.privaterequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.NewEventDto;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.dto.UpdateEventRequest;
    import ru.practicum.explorewithme.privaterequest.service.PrivateUserEventService;

    import javax.validation.Valid;
    import javax.validation.constraints.NotNull;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/users/{userId}/events")
    @Slf4j
    @RequiredArgsConstructor
    public class PrivateUserEventController {
        final private PrivateUserEventService privateUserEventService;

//        @Autowired
//        public PrivateUserEventController (PrivateUserEventService privateUserEventService) {
//            this.privateUserEventService = privateUserEventService;
//        }

        @GetMapping
        public List<EventShortDto> getUserEvents(
             @PathVariable Long userId,
             @RequestParam(name = "from", defaultValue = "0") Integer from,
             @RequestParam(name = "size", defaultValue = "10") Integer size)
                    throws ObjectNotFoundException, RequestErrorException {
            log.info("privateUserEvents, get categories with userId={}, from={}, size={}",
                    userId, from, size);
            return privateUserEventService.getUserEvents(userId, from, size);
        }

        @PatchMapping
        public EventFullDto updateUserEvent(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateEventRequest updateEventRequest)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("patchUserEvent, patch user event with userId={}, updateEventRequest {}",
                userId, updateEventRequest);
            return privateUserEventService.patchUserIdEvent(userId, updateEventRequest);
        }

        @PostMapping
        public EventFullDto addEvent(
            @PathVariable Long userId,
            @Valid @RequestBody NewEventDto newEventDto) throws ObjectNotFoundException, RequestErrorException {
            log.info("createEvent, create event with userId={}, newEventDto {}", userId, newEventDto);
            return privateUserEventService.createUserEvent(userId, newEventDto);
        }

        @GetMapping("/{eventId}")
        public EventFullDto getUserEventById(
            @PathVariable Long userId,
            @PathVariable Long eventId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privateUserEventById get user event by userId={}, eventId={}", userId, eventId);
            return privateUserEventService.getUserEventById(userId, eventId);
        }

        @PatchMapping("/{eventId}")
        public EventFullDto updateСancelEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("patchСancelEvent, сancel event with userId={}, eventId={}", userId, eventId);
            return privateUserEventService.patchCancelUserIdEvent(userId, eventId);
        }

        @GetMapping("/{eventId}/requests")
        public List<ParticipationRequestDto> getUserEventRequests(
            @PathVariable Long userId,
            @PathVariable Long eventId) throws ObjectNotFoundException, RequestErrorException {
            log.info("privateUserEventRequests get requests user event by userId={}, eventId={}",
                 userId, eventId);
            return privateUserEventService.getUserEventRequestsById(userId, eventId);
        }
        @PatchMapping("/{eventId}/requests/{reqId}/confirm")
        public ParticipationRequestDto updateRequestConfirm(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privatePatchRequestConfirm, confirm request userId={}, eventId={}, reqId={}",
                userId, eventId, reqId);
            return privateUserEventService.patchUserRequestConfirm(userId, eventId, reqId);
        }

        @PatchMapping("/{eventId}/requests/{reqId}/reject")
        public ParticipationRequestDto updateRequestReject(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privatePatchRequestReject, confirm request userId={}, eventId={}, reqId={}",
                userId, eventId, reqId);
            return privateUserEventService.patchUserRequestReject(userId, eventId, reqId);
        }

    }