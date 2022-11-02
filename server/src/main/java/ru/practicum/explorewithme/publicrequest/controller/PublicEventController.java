    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
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
        public List<EventShortDto> publicGetEvents(
            @RequestParam(name = "text") String text,
            @RequestParam(name = "categories") List<Long> categories,
            @RequestParam(name = "paid") Boolean paid,
            @RequestParam(name = "rangeStart", required = false) String rangeStart,
            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            HttpServletRequest request) throws ObjectNotFoundException {

           log.info("publicGetEvents, get events with text={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                   text, rangeStart, rangeEnd, from, size);
           return publicEventService.getEventsByTextAndCategory(text, categories, paid, rangeStart,
                   rangeEnd, onlyAvailable, sort, from, size, request);
        }

        @GetMapping("/{id}")
        public List<EventFullDto> publicGetEventsWithId(
            @NotBlank @PathVariable Long id, HttpServletRequest request) throws ObjectNotFoundException, RequestErrorException {
            log.info("publicGetEventsWithId get event by id={}", id);
            return publicEventService.getEventById(id, request);
        }

    }
