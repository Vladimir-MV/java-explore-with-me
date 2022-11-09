    package ru.practicum.explorewithme.admin.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminEventService;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.dto.AdminUpdateEventRequest;

    import javax.validation.constraints.Positive;
    import javax.validation.constraints.PositiveOrZero;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/admin/events")
    @Slf4j
    @Validated
    @RequiredArgsConstructor
    public class AdminEventController {

        final private AdminEventService adminEventService;

        @GetMapping
        public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                            @RequestParam(name = "states", required = false) List<String> states,
                                            @RequestParam(name = "categories", required = false) List<Long> categories,
                                            @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size)
                throws ObjectNotFoundException {
            log.info("adminGetEvents, get events users={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                    users, rangeStart, rangeEnd, from, size);
            return adminEventService.getEventsByUsersStatesCategories(users, states, categories, rangeStart,
                    rangeEnd, from, size);
        }


        @PutMapping("/{eventId}")
        public EventFullDto updateEvent(@PathVariable Long eventId,
                                        @RequestBody AdminUpdateEventRequest adminUpdateEventRequest)
                throws ObjectNotFoundException {
            log.info("adminPutUserEvent, put event eventId={}, adminUpdateEventRequest {}",
                    eventId, adminUpdateEventRequest);
            return adminEventService.putEventById(eventId, adminUpdateEventRequest);
        }

        @PatchMapping("/{eventId}/publish")
        public EventFullDto updatePublishEvent(@PathVariable Long eventId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("adminPublishEvent, publish event eventId={}", eventId);
            return adminEventService.patchPublishEventById(eventId);
        }

        @PatchMapping("/{eventId}/reject")
        public EventFullDto updateRejectEvent(@PathVariable Long eventId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("adminRejectEvent, reject event eventId={}", eventId);
            return adminEventService.patchRejectEventById(eventId);
        }
    }
