    package ru.practicum.explorewithme.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.model.EndpointHit;
    import ru.practicum.explorewithme.model.ViewStats;
    import ru.practicum.explorewithme.service.StatsService;
    import javax.validation.constraints.NotNull;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @Slf4j
    @RequiredArgsConstructor
    public class StatsController {
        final private StatsService statsService;

        @PostMapping("/hit")
        public void addEndpointHit(@RequestBody EndpointHit endpointHit) {
            log.info("createEndpointHit, create endpoint hit endpointHit={}", endpointHit);
            statsService.createNewEndpointHit(endpointHit);
        }
        @GetMapping("/stats")
        public List<ViewStats> getViewStats(@RequestParam(name = "start") Optional<String> start,
                                            @RequestParam(name = "end") Optional<String> end,
                                            @RequestParam(name = "uris") Optional<List<String>> uris,
                                            @RequestParam(name = "unique", defaultValue = "false") Boolean unique)
                    throws ObjectNotFoundException{
            log.info("getViewStats, get view stats start={}, end={}, uris {}, unique={}",
                    start, end, uris, unique);
            return statsService.getListViewStats(start, end, uris, unique);
        }

    }
