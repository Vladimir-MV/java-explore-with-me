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
    import java.util.List;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PublicEventServiceImpl implements PublicEventService {

        private final RestTemplateClientStat restTemplateClientStat;
        private final EventRepository eventRepository;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Transactional(readOnly = true)
        @Override
        public List<EventShortDto> getEventsByTextAndCategory(
                String text, List<Long> categories,
                Boolean paid, String rangeStart,
                String rangeEnd, boolean onlyAvailable,
                String sort, Integer from, Integer size,
                HttpServletRequest request) throws ObjectNotFoundException {

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
              throw new ObjectNotFoundException("Объект не найден. ",
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
        public EventFullDto getEventById(Long id, HttpServletRequest request)
                throws ObjectNotFoundException{
            Event event = eventRepository.findByIdAndState(id, State.PUBLISHED).orElseThrow(
                    () -> new ObjectNotFoundException("Объект не найден. ",
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
        public List<EventShortLocationDto> getEventByLocationId(Long id) throws ObjectNotFoundException {
            List<Event> eventList = eventRepository.findEventsByLocationId(id).orElseThrow(
                    () -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("LocationGroupList locationGroupList {} was not found.")));
            log.info("Найден список событий в локации(группе) id={}", id);
            return EventMapper.toListEventShortLocationDto(eventList);
        }
    }
