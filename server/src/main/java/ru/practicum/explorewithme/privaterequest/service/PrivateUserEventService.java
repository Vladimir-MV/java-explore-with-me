    package ru.practicum.explorewithme.privaterequest.service;

    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.EventShortDto;
    import ru.practicum.explorewithme.dto.NewEventDto;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.dto.UpdateEventRequest;

    import java.util.List;

    public interface PrivateUserEventService {

        EventFullDto getUserEventById(Long catId, Long eventId);

        List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size);

        EventFullDto patchUserIdEvent(Long userId, UpdateEventRequest updateEventRequest);

        EventFullDto createUserEvent(Long userId, NewEventDto newEventDto);

        List<ParticipationRequestDto> getUserEventRequestsById(Long userId, Long eventId);

        ParticipationRequestDto patchUserRequestConfirm(Long userId, Long eventId, Long reqId);

        ParticipationRequestDto patchUserRequestReject(Long userId, Long eventId, Long reqId);

        EventFullDto patchCancelUserIdEvent(Long userId, Long eventId);
    }
