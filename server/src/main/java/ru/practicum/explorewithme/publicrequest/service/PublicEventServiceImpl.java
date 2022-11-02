    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.client.RestTemplateClientStat;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.EventMapper;
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
    import java.util.Optional;

    @Slf4j
    @Service
    public class PublicEventServiceImpl implements PublicEventService{
        private RestTemplateClientStat restTemplateClientStat;
        private EventRepository eventRepository;
        private UserRepository userRepository;
        private CategoryRepository categoryRepository;

        @Autowired
        public PublicEventServiceImpl (EventRepository eventRepository,  UserRepository userRepository,
                      RestTemplateClientStat restTemplateClientStat, CategoryRepository categoryRepository) {
            this.restTemplateClientStat =restTemplateClientStat;
            this.eventRepository = eventRepository;
            this.userRepository = userRepository;
            this.categoryRepository = categoryRepository;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      //  LocalDateTime anotherDateTime = LocalDateTime.parse("22.02.2022, 22:22", formatter);
        @Override
        public List<EventShortDto> getEventsByTextAndCategory(String text, List<Long> categories,
                               Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort,
                               Integer from, Integer size, HttpServletRequest request) throws ObjectNotFoundException {

            List<Event> listEvent = new ArrayList<>();
            final Pageable pageable = FromSizeRequest.of(from, size);
            if (rangeStart.isEmpty()) rangeStart = LocalDateTime.now().format(formatter);
            if (sort.equals("EVENT_DATE") && onlyAvailable == false && rangeEnd.isEmpty())
                listEvent = eventRepository.searchEventByEventDayAvailableFalseEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();

            if (sort.equals("EVENT_DATE") && onlyAvailable == false && !rangeEnd.isEmpty())
                listEvent = eventRepository.searchEventByEventDayAvailableFalseEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter), pageable).getContent();

            if (sort.equals("EVENT_DATE") && onlyAvailable == true && rangeEnd.isEmpty())
                listEvent = eventRepository.searchEventByEventDayAvailableTrueEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();

            if (sort.equals("EVENT_DATE") && onlyAvailable == true && !rangeEnd.isEmpty())
                listEvent = eventRepository.searchEventByEventDayAvailableTrueEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter),  pageable).getContent();

            if (sort.equals("VIEWS") && onlyAvailable == false && rangeEnd.isEmpty())
                listEvent = eventRepository.searchEventByViewsAvailableFalseEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();

            if (sort.equals("VIEWS") && onlyAvailable == false && !rangeEnd.isEmpty())
                listEvent = eventRepository.searchEventByViewsAvailableFalseEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter),  pageable).getContent();

            if (sort.equals("VIEWS") && onlyAvailable == true && rangeEnd.isEmpty())
                listEvent = eventRepository.searchEventByViewsAvailableTrueEndNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), pageable).getContent();

            if (sort.equals("VIEWS") && onlyAvailable == true && !rangeEnd.isEmpty())
                listEvent = eventRepository.searchEventByViewsAvailableTrueEndNotNull(text, categories, paid,
                        LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter),  pageable).getContent();

          if (!(listEvent.size() > 0)) throw new ObjectNotFoundException(String.format("Events with text = {} was not found.", text));
            EndpointHit endpointHit = new EndpointHit();
            endpointHit.setUri(request.getRequestURI());
            endpointHit.setIp(request.getRemoteAddr());
            endpointHit.setTimestamp(LocalDateTime.now());
            restTemplateClientStat.createEndpointHitStatistics(endpointHit);
          return EventMapper.toListEventShortDto(listEvent);
        }

        @Override
        public List<EventFullDto> getEventById(Long id, HttpServletRequest request)
                throws ObjectNotFoundException, RequestErrorException {
            //if (!id.isPresent())  throw new RequestErrorException();
            List<Event> listEvent = eventRepository.findByEvent_IdAndState(id).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Events with id={} was not found.", id)));
            //if (listEvent.isPresent()) {
             //   List<Event> list = listEvent.get();
            for (Event event: listEvent){
                    event.setViews(event.getViews() + 1);
                    eventRepository.saveAndFlush(event);
                }
                EndpointHit endpointHit = new EndpointHit();
                endpointHit.setUri(request.getRequestURI());
                endpointHit.setIp(request.getRemoteAddr());
                endpointHit.setTimestamp(LocalDateTime.now());
                restTemplateClientStat.createEndpointHitStatistics(endpointHit);
                return EventMapper.toListEventFullDto(listEvent);
//            } else {
//                throw new ObjectNotFoundException(String.format("Event with id={} was not found.", id.get()));
//            }
        }
    }
