    package ru.practicum.explorewithme.admin.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminEventService;
    import ru.practicum.explorewithme.admin.service.AdminEventServiceImpl;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.model.AdminUpdateEventRequest;

    import java.util.List;
    import java.util.Optional;

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
                @RequestParam(name = "users", defaultValue = "") Long[] users,
                @RequestParam(name = "states", defaultValue = "") String[] states,
                @RequestParam(name = "categories", defaultValue = "") Long[] categories,
                @RequestParam(name = "rangeStart", defaultValue = "") String rangeStart,
                @RequestParam(name = "rangeEnd", defaultValue = "") String rangeEnd,
                @RequestParam(name = "from", defaultValue = "0") Integer from,
                @RequestParam(name = "size", defaultValue = "10") Integer size) {
            log.info("adminGetEvents, get events users={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                    users, rangeStart, rangeEnd, from, size);
            return adminEventService.getEventsByUsersStatesCategories(users, states, categories, rangeStart,
                    rangeEnd, from, size);
        }


        @PutMapping("/{eventId}")
        public AdminUpdateEventRequest adminPutEvent(
                @PathVariable Optional<Long> eventId,
                @RequestBody Optional<AdminUpdateEventRequest> adminUpdateEventRequest) {
            log.info("adminPutUserEvent, put event eventId={}, adminUpdateEventRequest {}", eventId, adminUpdateEventRequest);
            return adminEventService.putEventById(eventId, adminUpdateEventRequest);
        }

        @PatchMapping("/{eventId}/publish")
        public EventFullDto adminPublishEvent(
                @PathVariable Optional<Long> eventId) {
            log.info("adminPublishEvent, publish event eventId={}", eventId);
            return adminEventService.patchPublishEventById(eventId);
        }

        @PatchMapping("/{eventId}/reject")
        public EventFullDto adminRejectEvent(
                @PathVariable Optional<Long> eventId) {
            log.info("adminRejectEvent, reject event eventId={}", eventId);
            return adminEventService.patchRejectEventById(eventId);
        }

    }
