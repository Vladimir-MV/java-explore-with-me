    package ru.practicum.explorewithme.service;

    import lombok.extern.slf4j.Slf4j;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
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
    public class StatsServiceImpl implements StatsService {
        private StatsRepository statsRepository;

        @Autowired
        public StatsServiceImpl(StatsRepository statsRepository) {
            this.statsRepository = statsRepository;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public void createNewEndpointHit(EndpointHit endpointHit) {
            statsRepository.saveAndFlush(endpointHit);
        }

        @Override
        public List<ViewStats> getListViewStats(Optional<String> start, Optional<String> end, Optional<List<String>> uris, Boolean unique)
                throws ObjectNotFoundException {
            List<ViewStats> listViews = new ArrayList<>();
            List<EndpointHit> listEndpoint = new ArrayList<>();
            if (unique) {
                if (uris.isPresent()) {
                    listEndpoint = statsRepository.findEndpointHitByUriUniqueTrue(
                            uris.get(),
                            LocalDateTime.parse(start.get(), formatter),
                            LocalDateTime.parse(end.get(), formatter)).orElseThrow(
                            () -> new ObjectNotFoundException(String.format("EndpointHit with uris {} was not found.", uris.get())));
                }
                if (!uris.isPresent()) {
                    listEndpoint = statsRepository.findEndpointHitByNotUriUniqueTrue(
                            LocalDateTime.parse(start.get(), formatter),
                            LocalDateTime.parse(end.get(), formatter)).orElseThrow(
                            () -> new ObjectNotFoundException(String.format("EndpointHit without uris {} was not found.", uris.get())));
                }

//
//                if (start.isPresent() && end.isPresent() && !uris.isEmpty() && unique) {
//                    r = storage.findByAllParam(LocalDateTime.parse(start.get(), DATE_FORMAT),
//                            LocalDateTime.parse(end.get(), DATE_FORMAT), uris, true);
//                }
//
//                for (Stats stats : r) {
//                    StatsView s = StatsDtoMapper.toDtoView(stats);
//                    s.setHits(storage.giveCount(stats.getUri()));
//                    r2.add(s);
//                    endpoint = statsRepository.findEndpointHitByUriUniqueFalse(
//                            uris.get(),
//                            LocalDateTime.parse(start.get(), formatter),
//                            LocalDateTime.parse(end.get(), formatter));
//                    .orElseThrow(
//                            () -> new ObjectNotFoundException(String.format("EndpointHit with uri {} was not found.", uri)));
//                            URLDecoder.decode(uri, "UTF-8"),
//                            LocalDateTime.parse(URLDecoder.decode(start, "UTF-8")),
//                            LocalDateTime.parse(URLDecoder.decode(end, "UTF-8")));
//                    if (endpointHit.isPresent()) {
//                        EndpointHit endpoint = endpointHit.ge;


            } else {

                if (uris.isPresent()) {
                    listEndpoint = statsRepository.findEndpointHitByUriUniqueFalse(
                            uris.get(),
                            LocalDateTime.parse(start.get(), formatter),
                            LocalDateTime.parse(end.get(), formatter)).orElseThrow(
                            () -> new ObjectNotFoundException(String.format("EndpointHit with uris {} was not found.", uris.get())));
                }

                if (!uris.isPresent()) {
                    listEndpoint = statsRepository.findEndpointHitByNotUriUniqueFalse(
                            LocalDateTime.parse(start.get(), formatter),
                            LocalDateTime.parse(end.get(), formatter)).orElseThrow(
                            () -> new ObjectNotFoundException(String.format("EndpointHit without uris {} was not found.", uris.get())));
                }

            }
            ViewStats viewStats = new ViewStats();
            for (EndpointHit endpoint: listEndpoint){
                viewStats.setApp(endpoint.getApp());
                viewStats.setUri(endpoint.getUri());
                viewStats.setHits(statsRepository.countUri(endpoint.getUri()));
                listViews.add(viewStats);
            }

            return listViews;
        }
    }


