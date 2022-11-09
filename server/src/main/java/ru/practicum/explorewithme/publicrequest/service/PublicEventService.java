    package ru.practicum.explorewithme.publicrequest.service;

    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.EventShortLocationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import javax.servlet.http.HttpServletRequest;
    import java.util.List;

    public interface PublicEventService {
        List<EventShortDto> getEventsByTextAndCategory(
                String text, List<Long> categories,
                Boolean rangeStart, String rangeEnd, String paid, boolean onlyAvailable, String sort,
                Integer from, Integer size, HttpServletRequest request) throws ObjectNotFoundException;

        EventFullDto getEventById(Long id, HttpServletRequest request)
                throws ObjectNotFoundException, RequestErrorException;

        List<EventShortLocationDto> getEventByLocationId(Long id) throws ObjectNotFoundException;
    }
