package ru.practicum.explorewithme.privaterequest.service;

import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;
import ru.practicum.explorewithme.model.UpdateEventRequest;

import java.util.List;
import java.util.Optional;

public interface PrivateUserEventService {
    EventFullDto getUserEventById(Optional<Long> catId, Optional<Long> eventId) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;

    List<EventShortDto> getUserEvents(Optional<Long> userId, Integer from, Integer size) throws ObjectNotFoundException, RequestErrorException;

    EventFullDto patchUserIdEvent(Optional<Long> userId, Optional<UpdateEventRequest> updateEventRequest) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;

    EventFullDto createUserEvent(Optional<Long> userId, Optional<NewEventDto> newEventDto) throws ObjectNotFoundException, RequestErrorException;

    ParticipationRequestDto getUserEventRequestsById(Optional<Long> userId, Optional<Long> eventId) throws ObjectNotFoundException, RequestErrorException;

    ParticipationRequestDto patchUserRequestConfirm(Optional<Long> userId, Optional<Long> eventId, Optional<Long> reqId) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;

    ParticipationRequestDto patchUserRequestReject(Optional<Long> userId, Optional<Long> eventId, Optional<Long> reqId) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;

    EventFullDto patchCancelUserIdEvent(Optional<Long> userId, Optional<Long> eventId) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;
}
