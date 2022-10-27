    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.model.AdminUpdateEventRequest;

    import java.util.List;
    import java.util.Optional;

    public interface AdminEventService {
        List<EventFullDto> getEventsByUsersStatesCategories(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size);

        AdminUpdateEventRequest putEventById(Optional<Long> eventId, Optional<AdminUpdateEventRequest> adminUpdateEventRequest);

        EventFullDto patchPublishEventById(Optional<Long> eventId);

        EventFullDto patchRejectEventById(Optional<Long> eventId);
    }
