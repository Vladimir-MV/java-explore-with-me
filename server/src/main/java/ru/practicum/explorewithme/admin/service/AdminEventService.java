    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.AdminUpdateEventRequest;

    import java.util.List;

    public interface AdminEventService {

        List<EventFullDto> getEventsByUsersStatesCategories(
                List<Long> users, List<String> states, List<Long> categories,
                String rangeStart, String rangeEnd, Integer from, Integer size);


        EventFullDto putEventById(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

        EventFullDto patchPublishEventById(Long eventId);

        EventFullDto patchRejectEventById(Long eventId);
    }
