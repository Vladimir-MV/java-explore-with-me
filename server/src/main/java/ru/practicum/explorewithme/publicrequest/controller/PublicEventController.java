    package ru.practicum.explorewithme.publicrequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.EventShortLocationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.publicrequest.service.PublicEventService;
    import javax.servlet.http.HttpServletRequest;
    import javax.validation.constraints.Positive;
    import javax.validation.constraints.PositiveOrZero;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/events")
    @Slf4j
    @Validated
    @RequiredArgsConstructor
    public class PublicEventController {

        final private PublicEventService publicEventService;

        @GetMapping
        public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                             @RequestParam(name = "categories", required = false) List<Long> categories,
                                             @RequestParam(name = "paid", required = false) Boolean paid,
                                             @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                             @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                             @RequestParam(name = "onlyAvailable", required = false) boolean onlyAvailable,
                                             @RequestParam(name = "sort", defaultValue = "VIEWS") String sort,
                                             @PositiveOrZero  @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                             HttpServletRequest request)
                throws ObjectNotFoundException {
            log.info("publicGetEvents, get events with text={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                   text, rangeStart, rangeEnd, from, size);
           return publicEventService.getEventsByTextAndCategory(text, categories, paid, rangeStart,
                   rangeEnd, onlyAvailable, sort, from, size, request);
        }

        @GetMapping("/{id}")
        public EventFullDto getEvent(@PathVariable Long id, HttpServletRequest request)
                throws ObjectNotFoundException, RequestErrorException {
            log.info("publicGetEventsWithId get event by id={}", id);
            return publicEventService.getEventById(id, request);
        }
        //Фича: поиск всех событий в конкретной локации.

        @GetMapping("/locations/{id}")
        public List<EventShortLocationDto> getEventsLocation(@PathVariable Long id)
                throws ObjectNotFoundException {
            log.info("publicGetEventsLocationId get events by location id={}", id);
            return publicEventService.getEventByLocationId(id);
        }
    }
