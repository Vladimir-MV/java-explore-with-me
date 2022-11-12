    package ru.practicum.explorewithme.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.model.EndpointHit;
    import ru.practicum.explorewithme.model.ViewStats;
    import ru.practicum.explorewithme.repository.StatsRepository;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class StatsServiceImpl implements StatsService {
        private final StatsRepository statsRepository;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Transactional
        @Override
        public void createNewEndpointHit(EndpointHit endpointHit) {
            statsRepository.saveAndFlush(endpointHit);
        }

        @Transactional(readOnly = true)
        @Override
        public List<ViewStats> getListViewStats(Optional<String> start, Optional<String> end,
                                                Optional<List<String>> uris, Boolean unique)
                                                    throws ObjectNotFoundException {
            List<ViewStats> listViews = new ArrayList<>();
            List<EndpointHit> listEndpoint = new ArrayList<>();
            if (unique) {
                if (uris.isPresent()) {
                    listEndpoint = statsRepository.findEndpointHitByUriUniqueTrue(uris.get(),
                                     LocalDateTime.parse(start.get(), formatter),
                                     LocalDateTime.parse(end.get(), formatter)).orElseThrow(
                                     () -> new ObjectNotFoundException("Объект не найден. ",
                                     String.format("EndpointHit with uris {} was not found.", uris.get())));
                }
                if (!uris.isPresent()) {
                    listEndpoint = statsRepository.findEndpointHitByNotUriUniqueTrue(LocalDateTime.parse(start.get(), formatter),
                                     LocalDateTime.parse(end.get(), formatter)).orElseThrow(
                                     () -> new ObjectNotFoundException("Объект не найден. ",
                                     String.format("EndpointHit without uris {} was not found.", uris.get())));
                }
            } else {
                if (uris.isPresent()) {
                    listEndpoint = statsRepository.findEndpointHitByUriUniqueFalse(uris.get(),
                                     LocalDateTime.parse(start.get(), formatter),
                                     LocalDateTime.parse(end.get(), formatter)).orElseThrow(
                                     () -> new ObjectNotFoundException("Объект не найден. ",
                                     String.format("EndpointHit with uris {} was not found.", uris.get())));
                }

                if (!uris.isPresent()) {
                    listEndpoint = statsRepository.findEndpointHitByNotUriUniqueFalse(
                            LocalDateTime.parse(start.get(), formatter),
                            LocalDateTime.parse(end.get(), formatter)).orElseThrow(
                            () -> new ObjectNotFoundException("Объект не найден. ",
                                 String.format("EndpointHit without uris {} was not found.", uris.get())));
                }
            }
            ViewStats viewStats = new ViewStats();
            for (EndpointHit endpoint: listEndpoint) {
                viewStats.setApp(endpoint.getApp());
                viewStats.setUri(endpoint.getUri());
                viewStats.setHits(statsRepository.countUri(endpoint.getUri()));
                listViews.add(viewStats);
            }
            return listViews;
        }
    }


