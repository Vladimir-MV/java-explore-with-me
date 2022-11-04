package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;

import java.util.List;
import java.util.Optional;

public interface StatsService {
    void createNewEndpointHit(EndpointHit endpointHit);

    List<ViewStats> getListViewStats(Optional<String> start, Optional<String> end, Optional<List<String>> uris, Boolean unique) throws ObjectNotFoundException;
}
