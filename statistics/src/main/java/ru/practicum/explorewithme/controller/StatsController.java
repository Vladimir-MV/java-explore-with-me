package ru.practicum.explorewithme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;
import ru.practicum.explorewithme.service.StatsService;
import ru.practicum.explorewithme.service.StatsServiceImpl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class StatsController {
    StatsService statsService;

    @Autowired
    public StatsController (StatsServiceImpl statsServiceImpl) {
        this.statsService = statsServiceImpl;
    }

    @PostMapping("/hit")
    public void createEndpointHit(
        @RequestBody Optional<EndpointHit> endpointHit) {
        log.info("createEndpointHit, create endpoint hit endpointHit={}", endpointHit);
        statsService.createNewEndpointHit(endpointHit);
    }
    @GetMapping()
    public List<ViewStats> getViewStats(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end,
            @RequestParam(name = "uris") Optional<String[]> uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) throws UnsupportedEncodingException {
        log.info("getViewStats, get view stats start={}, end={}, uris {}, unique={}", start, end, uris, unique);
        return statsService.getListViewStats(start, end, uris, unique);
    }

}
