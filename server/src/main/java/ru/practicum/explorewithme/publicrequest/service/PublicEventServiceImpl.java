    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
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
    import ru.practicum.explorewithme.mapper.LocationGroupMapper;
    import ru.practicum.explorewithme.model.*;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import ru.practicum.explorewithme.repository.UserRepository;

    import javax.servlet.http.HttpServletRequest;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PublicEventServiceImpl implements PublicEventService{
        final private RestTemplateClientStat restTemplateClientStat;
        final private EventRepository eventRepository;
        final private UserRepository userRepository;
        final private CategoryRepository categoryRepository;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        @Transactional(readOnly = true)
        @Override
        public List<EventShortDto> getEventsByTextAndCategory(
                String text, List<Long> categories,
                Boolean paid, String rangeStart,
                String rangeEnd, Boolean onlyAvailable,
                String sort, Integer from, Integer size,
                HttpServletRequest request) throws ObjectNotFoundException {

            List<Event> listEvent = new ArrayList<>();
            final Pageable pageable = FromSizeRequest.of(from, size);
            if (rangeStart.isEmpty()) {
                rangeStart = LocalDateTime.now().format(formatter);
            }
            if (sort.equals("EVENT_DATE") && onlyAvailable == false && rangeEnd.isEmpty()) {
                listEvent = eventRepository
                        .searchEventByEventDayAvailableFalseEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();
            }

            if (sort.equals("EVENT_DATE") && onlyAvailable == false && !rangeEnd.isEmpty()) {
                listEvent = eventRepository
                        .searchEventByEventDayAvailableFalseEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter),
                        LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }

            if (sort.equals("EVENT_DATE") && onlyAvailable == true && rangeEnd.isEmpty()) {
                listEvent = eventRepository
                        .searchEventByEventDayAvailableTrueEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();
            }

            if (sort.equals("EVENT_DATE") && onlyAvailable == true && !rangeEnd.isEmpty()) {
                listEvent = eventRepository
                        .searchEventByEventDayAvailableTrueEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter),
                        LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }

            if (sort.equals("VIEWS") && onlyAvailable == false && rangeEnd.isEmpty()) {
                listEvent = eventRepository
                        .searchEventByViewsAvailableFalseEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();
            }

            if (sort.equals("VIEWS") && onlyAvailable == false && !rangeEnd.isEmpty()) {
                listEvent = eventRepository
                        .searchEventByViewsAvailableFalseEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter),
                        LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }
            if (sort.equals("VIEWS") && onlyAvailable == true && rangeEnd.isEmpty()) {
                listEvent = eventRepository
                        .searchEventByViewsAvailableTrueEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();
            }

            if (sort.equals("VIEWS") && onlyAvailable == true && !rangeEnd.isEmpty()) {
                listEvent = eventRepository
                        .searchEventByViewsAvailableTrueEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter),
                        LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();
            }

          if (!(listEvent.size() > 0)) {
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
            Event event = eventRepository.findByEventIdAndState(id, State.PUBLISHED).orElseThrow(
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
