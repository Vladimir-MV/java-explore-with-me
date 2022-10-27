package ru.practicum.explorewithme.privaterequest.service;

import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
import ru.practicum.explorewithme.model.UpdateEventRequest;

import java.util.List;
import java.util.Optional;

public interface PrivateUserEventService {
    EventFullDto getUserEventById(Optional<Long> catId, Optional<Long> eventId) throws MethodExceptions;

    List<EventShortDto> getUserEvents(Optional<Long> userId, Integer from, Integer size) throws MethodExceptions;

    EventFullDto patchUserIdEvent(Optional<Long> userId, Optional<UpdateEventRequest> updateEventRequest) throws MethodExceptions;

    EventFullDto createUserEvent(Optional<Long> userId, Optional<NewEventDto> newEventDto) throws MethodExceptions;

    ParticipationRequestDto getUserEventRequestsById(Optional<Long> userId, Optional<Long> eventId) throws MethodExceptions;

    ParticipationRequestDto patchUserRequestConfirm(Optional<Long> userId, Optional<Long> eventId, Optional<Long> reqId) throws MethodExceptions;

    ParticipationRequestDto patchUserRequestReject(Optional<Long> userId, Optional<Long> eventId, Optional<Long> reqId) throws MethodExceptions;

    EventFullDto patchCancelUserIdEvent(Optional<Long> userId, Optional<Long> eventId) throws MethodExceptions;
}
