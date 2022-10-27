    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.publicrequest.service.PublicEventService;
    import ru.practicum.explorewithme.publicrequest.service.PublicEventServiceImpl;

    import javax.servlet.http.HttpServletRequest;
    import javax.validation.constraints.NotBlank;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/events")
    @Slf4j
    public class PublicEventController {
        PublicEventService publicEventService;

        @Autowired
        public PublicEventController(PublicEventServiceImpl publicEventServiceImpl) {
            this.publicEventService = publicEventServiceImpl;
        }

        @GetMapping
        public EventShortDto[] publicGetEvents(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "categories", required = false) CategoryDto[] categories,
            @RequestParam(name = "paid", required = false) Boolean paid,
            @RequestParam(name = "rangeStart", required = false) String rangeStart,
            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            HttpServletRequest request) {

           log.info("publicGetEvents, get events with text={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                   text, rangeStart, rangeEnd, from, size);
           return publicEventService.getEventsByTextAndCategory(text, categories, paid, rangeStart,
                   rangeEnd, onlyAvailable, sort, from, size, request);
        }

        @GetMapping("/{id}")
        public List<EventFullDto> publicGetEventsWithId(
            @NotBlank @PathVariable Optional<Long> id, HttpServletRequest request) throws MethodExceptions {
            log.info("publicGetEventsWithId get event by id={}", id);
            return publicEventService.getEventById(id, request);
        }

    }
