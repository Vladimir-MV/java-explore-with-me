    package ru.practicum.explorewithme.privaterequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.NewEventDto;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.dto.UpdateEventRequest;
    import ru.practicum.explorewithme.privaterequest.service.PrivateUserEventService;

    import javax.validation.Valid;
    import javax.validation.constraints.Positive;
    import javax.validation.constraints.PositiveOrZero;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/users/{userId}/events")
    @Slf4j
    @Validated
    @RequiredArgsConstructor
    public class PrivateUserEventController {
        private final PrivateUserEventService privateUserEventService;

        @GetMapping
        public List<EventShortDto> getUserEvents(@PathVariable Long userId,
                                                 @PositiveOrZero @RequestParam(name = "from",
                                                         defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(name = "size",
                                                         defaultValue = "10") Integer size) {
            log.info("privateUserEvents, get categories with userId={}, from={}, size={}",
                    userId, from, size);
            return privateUserEventService.getUserEvents(userId, from, size);
        }


        @PatchMapping
        public EventFullDto updateUserEvent(@PathVariable Long userId,
                                            @Valid @RequestBody UpdateEventRequest updateEventRequest) {
            log.info("patchUserEvent, patch user event with userId={}, updateEventRequest {}",
                userId, updateEventRequest);
            return privateUserEventService.patchUserIdEvent(userId, updateEventRequest);
        }

        @PostMapping
        public EventFullDto addEvent(@PathVariable Long userId,
                                     @Valid @RequestBody NewEventDto newEventDto) {
            log.info("createEvent, create event with userId={}, newEventDto {}", userId, newEventDto);
            return privateUserEventService.createUserEvent(userId, newEventDto);
        }

        @GetMapping("/{eventId}")
        public EventFullDto getUserEventById(@PathVariable Long userId,
                                             @PathVariable Long eventId) {
            log.info("privateUserEventById get user event by userId={}, eventId={}", userId, eventId);
            return privateUserEventService.getUserEventById(userId, eventId);
        }

        @PatchMapping("/{eventId}")
        public EventFullDto updateСancelEvent(@PathVariable Long userId,
                                              @PathVariable Long eventId) {
            log.info("patchСancelEvent, сancel event with userId={}, eventId={}", userId, eventId);
            return privateUserEventService.patchCancelUserIdEvent(userId, eventId);
        }

        @GetMapping("/{eventId}/requests")
        public List<ParticipationRequestDto> getUserEventRequests(@PathVariable Long userId,
                                                                  @PathVariable Long eventId) {
            log.info("privateUserEventRequests get requests user event by userId={}, eventId={}",
                 userId, eventId);
            return privateUserEventService.getUserEventRequestsById(userId, eventId);
        }

        @PatchMapping("/{eventId}/requests/{reqId}/confirm")
        public ParticipationRequestDto updateRequestConfirm(@PathVariable Long userId,
                                                            @PathVariable Long eventId,
                                                            @PathVariable Long reqId) {
            log.info("privatePatchRequestConfirm, confirm request userId={}, eventId={}, reqId={}",
                userId, eventId, reqId);
            return privateUserEventService.patchUserRequestConfirm(userId, eventId, reqId);
        }

        @PatchMapping("/{eventId}/requests/{reqId}/reject")
        public ParticipationRequestDto updateRequestReject(@PathVariable Long userId,
                                                           @PathVariable Long eventId,
                                                           @PathVariable Long reqId) {
            log.info("privatePatchRequestReject, confirm request userId={}, eventId={}, reqId={}",
                userId, eventId, reqId);
            return privateUserEventService.patchUserRequestReject(userId, eventId, reqId);
        }
    }