    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.client.RestTemplateClientStat;
    import ru.practicum.explorewithme.dto.EndpointHitDto;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.EventShortLocationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.EventMapper;
    import ru.practicum.explorewithme.model.*;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import ru.practicum.explorewithme.repository.LocationGroupRepository;

    import javax.servlet.http.HttpServletRequest;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PublicEventServiceImpl implements PublicEventService {

        private final RestTemplateClientStat restTemplateClientStat;
        private final EventRepository eventRepository;
        private final LocationGroupRepository locationGroupRepository;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Transactional(readOnly = true)
        @Override
        public List<EventShortDto> getEventsByTextAndCategory(
                String text, List<Long> categories,
                Boolean paid, String rangeStart,
                String rangeEnd, boolean onlyAvailable,
                String sort, Integer from, Integer size,
                HttpServletRequest request) {

            List<Event> listEvent = new ArrayList<>();
            final Pageable pageable = FromSizeRequest.of(from, size);
            if (rangeStart == null) {
                rangeStart = LocalDateTime.now().format(formatter);
            }

            if (sort.equals("EVENT_DATE") && !onlyAvailable && rangeEnd == null) {
                listEvent = eventRepository
                        .searchEventByEventDayAvailableFalseEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();
            }

            if (sort.equals("EVENT_DATE") && !onlyAvailable && rangeEnd != null) {
                listEvent = eventRepository
                        .searchEventByEventDayAvailableFalseEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter),
                        LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }

            if (sort.equals("EVENT_DATE") && onlyAvailable && rangeEnd == null) {
                listEvent = eventRepository
                        .searchEventByEventDayAvailableTrueEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();
            }

            if (sort.equals("EVENT_DATE") && onlyAvailable && rangeEnd != null) {
                listEvent = eventRepository
                        .searchEventByEventDayAvailableTrueEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter),
                        LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }

            if (sort.equals("VIEWS") && !onlyAvailable && rangeEnd == null) {
                listEvent = eventRepository
                        .searchEventByViewsAvailableFalseEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();
            }

            if (sort.equals("VIEWS") && !onlyAvailable && rangeEnd != null) {
                listEvent = eventRepository
                        .searchEventByViewsAvailableFalseEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter),
                        LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }
            if (sort.equals("VIEWS") && onlyAvailable && rangeEnd == null) {
                listEvent = eventRepository
                        .searchEventByViewsAvailableTrueEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();
            }

            if (sort.equals("VIEWS") && onlyAvailable && rangeEnd != null) {
                listEvent = eventRepository
                        .searchEventByViewsAvailableTrueEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter),
                        LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }

          if (listEvent.isEmpty()) {
              new ObjectNotFoundException("Объект не найден. ",
                      String.format("Events with text = {} was not found.", text));
          }
            EndpointHitDto endpointHit = new EndpointHitDto();
            endpointHit.setUri(request.getRequestURI());
            endpointHit.setIp(request.getRemoteAddr());
            endpointHit.setTimestamp(LocalDateTime.now());
            restTemplateClientStat.createEndpointHitStatistics(endpointHit);
          return EventMapper.toListEventShortDto(listEvent);
        }

        @Transactional
        @Override
        public EventFullDto getEventById(Long id, HttpServletRequest request) {
            Event event = eventRepository.findByIdAndState(id, State.PUBLISHED)
                    .orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("Events with id={} was not found.", id)));
            event.setViews(event.getViews() + 1);
            eventRepository.saveAndFlush(event);
            EndpointHitDto endpointHit = new EndpointHitDto();
            endpointHit.setUri(request.getRequestURI());
            endpointHit.setIp(request.getRemoteAddr());
            endpointHit.setTimestamp(LocalDateTime.now());
            restTemplateClientStat.createEndpointHitStatistics(endpointHit);
            return EventMapper.toEventFullDto(event);
        }

        @Transactional(readOnly = true)
        @Override
        public Set<EventShortLocationDto> getEventByLocationId(Long id) {
            Set<Event> eventSet = locationGroupRepository.findEventsByLocationId(id).orElseThrow(() ->
                    new ObjectNotFoundException("Объект не найден. ",
                            String.format("LocationGroupList locationGroupList {} was not found.")));
            List<LocationGroup> locationGroupList = locationGroupRepository.findAll();
            Set<LocationGroup> locationGroupSet = new HashSet<>();
            for (Event event: eventSet) {
                   for (LocationGroup locationGroup : locationGroupList) {
                       if (locationGroup.getRadius() >= locationGroupRepository
                               .distanceBetweenLocations(locationGroup.getLat(), locationGroup.getLon(),
                                       event.getLocation().getLat(), event.getLocation().getLon())) {
                           locationGroupSet.add(locationGroup);
                       }
                   }
            event.setLocationGroup(locationGroupSet);
            eventRepository.save(event);
            }
            log.info("Найден список событий в локации(группе) id={}", id);
            return EventMapper.toSetEventShortLocationDto(eventSet);
        }
    }
