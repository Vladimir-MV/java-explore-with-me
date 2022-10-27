    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.client.RestTemplateClientStat;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.mapper.EventMapper;
    import ru.practicum.explorewithme.model.EndpointHit;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;

    import javax.servlet.http.HttpServletRequest;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.List;
    import java.util.Optional;

    @Slf4j
    @Service
    public class PublicEventServiceImpl implements PublicEventService{
        private RestTemplateClientStat restTemplateClientStat;
        private EventRepository eventRepository;

        @Autowired
        public PublicEventServiceImpl (EventRepository eventRepository, RestTemplateClientStat restTemplateClientStat) {
            this.restTemplateClientStat =restTemplateClientStat;
            this.eventRepository = eventRepository;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      //  LocalDateTime anotherDateTime = LocalDateTime.parse("22.02.2022, 22:22", formatter);
        @Override
        public EventShortDto[] getEventsByTextAndCategory(String text, CategoryDto[] categories, Boolean paid,
            String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request){
            final Pageable pageable = FromSizeRequest.of(from, size);
            for (CategoryDto cat: categories) {
                if (rangeStart) {

                    rangeStart = LocalDateTime.now().format(formatter);
                }
            }

//            List<Event> listEvent = eventRepository
//            Optional<List<Event>> listEvent = eventRepository.findByEvent_IdAndState(id.get());
//            if (listEvent.isPresent()) {
//                EndpointHit endpointHit = new EndpointHit();
//                endpointHit.setUri(request.getRequestURI());
//                endpointHit.setIp(request.getRemoteAddr());
//                endpointHit.setTimestamp(LocalDateTime.now().format(formatter));
//                restTemplateClientStat.createEndpointHitStatistics(endpointHit);
//                return EventMapper.toListEventFullDto(listEvent.get());
//            } else {
//                throw new MethodExceptions(String.format("Event with id={} was not found.", id.get()),
//                        404, "The required object was not found.");
//            }
    //                .findAllEventsByTextAndCategory(idUser.get(), pageable).getContent();
    //        log.info("Вся бронь пользователя {}, со статусом ALL", user.getName());
    //        return BookingMapper.toListBookingDto(listBooking);
            return null;
        }

        @Override
        public List<EventFullDto> getEventById(Optional<Long> id, HttpServletRequest request) throws MethodExceptions {
            if (!id.isPresent())  throw new MethodExceptions("For the requested operation the conditions are not met.",
                                                              400, "The required object was not found.");
            Optional<List<Event>> listEvent = eventRepository.findByEvent_IdAndState(id.get());
            if (listEvent.isPresent()) {
                List<Event> list = listEvent.get();
                for (Event event: list){
                    event.setViews(event.getViews() + 1);
                    eventRepository.save(event);
                }
                EndpointHit endpointHit = new EndpointHit();
                endpointHit.setUri(request.getRequestURI());
                endpointHit.setIp(request.getRemoteAddr());
                endpointHit.setTimestamp(LocalDateTime.now());
                restTemplateClientStat.createEndpointHitStatistics(endpointHit);
                return EventMapper.toListEventFullDto(list);
            } else {
                throw new MethodExceptions(String.format("Event with id={} was not found.", id.get()),
                                     404, "The required object was not found.");
            }
        }
    }
