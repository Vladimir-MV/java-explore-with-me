package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface StatsService {
    void createNewEndpointHit(Optional<EndpointHit> endpointHit);

    List<ViewStats> getListViewStats(String start, String end, Optional<String[]> uris, Boolean unique) throws UnsupportedEncodingException;
}
