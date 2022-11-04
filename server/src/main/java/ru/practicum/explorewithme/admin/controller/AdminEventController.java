    package ru.practicum.explorewithme.admin.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminEventService;
    import ru.practicum.explorewithme.admin.service.AdminEventServiceImpl;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.dto.AdminUpdateEventRequest;

    import java.util.List;

    @RestController
    @RequestMapping(path = "/admin/events")
    @Slf4j
    public class AdminEventController {

        AdminEventService adminEventService;

        @Autowired
        public AdminEventController(AdminEventServiceImpl adminEventServiceImpl) {
            this.adminEventService = adminEventServiceImpl;
        }

        @GetMapping
        public List<EventFullDto> adminGetEvents(
                @RequestParam(name = "users") List<Long> users,
                @RequestParam(name = "states") List<String> states,
                @RequestParam(name = "categories") List<Long> categories,
                @RequestParam(name = "rangeStart") String rangeStart,
                @RequestParam(name = "rangeEnd") String rangeEnd,
                @RequestParam(name = "from", defaultValue = "0") Integer from,
                @RequestParam(name = "size", defaultValue = "10") Integer size) throws ObjectNotFoundException {
            log.info("adminGetEvents, get events users={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                    users, rangeStart, rangeEnd, from, size);
            return adminEventService.getEventsByUsersStatesCategories(users, states, categories, rangeStart,
                    rangeEnd, from, size);
        }


        @PutMapping("/{eventId}")
        public EventFullDto adminPutEvent(
                @PathVariable Long eventId,
                @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) throws ObjectNotFoundException {
            log.info("adminPutUserEvent, put event eventId={}, adminUpdateEventRequest {}", eventId, adminUpdateEventRequest);
            return adminEventService.putEventById(eventId, adminUpdateEventRequest);
        }

        @PatchMapping("/{eventId}/publish")
        public EventFullDto adminPublishEvent(
                @PathVariable Long eventId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("adminPublishEvent, publish event eventId={}", eventId);
            return adminEventService.patchPublishEventById(eventId);
        }

        @PatchMapping("/{eventId}/reject")
        public EventFullDto adminRejectEvent(
                @PathVariable Long eventId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("adminRejectEvent, reject event eventId={}", eventId);
            return adminEventService.patchRejectEventById(eventId);
        }

    }
