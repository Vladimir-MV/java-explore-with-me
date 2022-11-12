    package ru.practicum.explorewithme.publicrequest.service;

    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.EventShortLocationDto;
    import javax.servlet.http.HttpServletRequest;
    import java.util.List;
    import java.util.Set;

    public interface PublicEventService {

        List<EventShortDto> getEventsByTextAndCategory(
                String text, List<Long> categories,
                Boolean rangeStart, String rangeEnd, String paid, boolean onlyAvailable, String sort,
                Integer from, Integer size, HttpServletRequest request);

        EventFullDto getEventById(Long id, HttpServletRequest request);

        Set<EventShortLocationDto> getEventByLocationId(Long id);
    }
