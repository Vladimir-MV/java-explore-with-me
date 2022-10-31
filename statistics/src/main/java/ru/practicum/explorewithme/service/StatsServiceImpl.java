    package ru.practicum.explorewithme.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.model.EndpointHit;
    import ru.practicum.explorewithme.model.ViewStats;
    import ru.practicum.explorewithme.repository.StatsRepository;
    import java.io.UnsupportedEncodingException;
    import java.net.URLDecoder;
    import java.time.LocalDateTime;
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

        @Override
        public void createNewEndpointHit(Optional<EndpointHit> endpointHit) {
            if (endpointHit.isPresent()) statsRepository.save(endpointHit.get());
        }

        @Override
        public List<ViewStats> getListViewStats(String start, String end, Optional<List<String>> uris, Boolean unique)
                throws UnsupportedEncodingException {
            List<ViewStats> list = new ArrayList<>();
            if (unique == false) {
                for (String uri : uris.get()) {
                    Optional<EndpointHit> endpointHit = statsRepository.findEndpointHitByUriUniqueFalse(
                                uri,
                            LocalDateTime.parse(start),
                            LocalDateTime.parse(end));
//                            URLDecoder.decode(uri, "UTF-8"),
//                            LocalDateTime.parse(URLDecoder.decode(start, "UTF-8")),
//                            LocalDateTime.parse(URLDecoder.decode(end, "UTF-8")));
                    if (endpointHit.isPresent()) {
                        EndpointHit endpoint = endpointHit.get();
                        list.add(new ViewStats(
                                endpoint.getApp(),
                                endpoint.getUri(),
                                endpoint.getHits()
                        ));
                        endpoint.setHits(endpoint.getHits() + 1);
                        statsRepository.save(endpoint);
                    }
                }
            } else {
                for (String uri : uris.get()) {
                    Optional<EndpointHit> endpointHit = statsRepository.findEndpointHitByUriUniqueTrue(
                                uri,
                                LocalDateTime.parse(start),
                                LocalDateTime.parse(end));
//                            URLDecoder.decode(uri, "UTF-8"),
//                            LocalDateTime.parse(URLDecoder.decode(start, "UTF-8")),
//                            LocalDateTime.parse(URLDecoder.decode(end, "UTF-8")));
                    if (endpointHit.isPresent()) {
                        EndpointHit endpoint = endpointHit.get();
                        list.add(new ViewStats(
                                endpoint.getApp(),
                                endpoint.getUri(),
                                endpoint.getHits()
                        ));
                        endpoint.setHits(endpoint.getHits() + 1);
                        statsRepository.save(endpoint);
                    }
                }
            }
            return list;
        }
    }


