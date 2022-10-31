    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.model.AdminUpdateEventRequest;

    import java.util.List;
    import java.util.Optional;

    public interface AdminEventService {
        List<EventFullDto> getEventsByUsersStatesCategories(List<Long> users, List<String> states, List<Long> categories,
                                                            String rangeStart, String rangeEnd, Integer from, Integer size)
                                                            throws ObjectNotFoundException;

        EventFullDto putEventById(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) throws ObjectNotFoundException;

        EventFullDto patchPublishEventById(Optional<Long> eventId) throws ObjectNotFoundException,
                RequestErrorException, ConditionsOperationNotMetException;

        EventFullDto patchRejectEventById(Optional<Long> eventId) throws ObjectNotFoundException,
                RequestErrorException, ConditionsOperationNotMetException;
    }
