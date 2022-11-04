package ru.practicum.explorewithme.privaterequest.service;

import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;
import ru.practicum.explorewithme.dto.UpdateEventRequest;

import java.util.List;

public interface PrivateUserEventService {
    EventFullDto getUserEventById(Long catId, Long eventId) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;

    List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size) throws ObjectNotFoundException, RequestErrorException;

    EventFullDto patchUserIdEvent(Long userId, UpdateEventRequest updateEventRequest) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;

    EventFullDto createUserEvent(Long userId, NewEventDto newEventDto) throws ObjectNotFoundException, RequestErrorException;

    List<ParticipationRequestDto> getUserEventRequestsById(Long userId, Long eventId) throws ObjectNotFoundException, RequestErrorException;

    ParticipationRequestDto patchUserRequestConfirm(Long userId, Long eventId, Long reqId) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;

    ParticipationRequestDto patchUserRequestReject(Long userId, Long eventId, Long reqId) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;

    EventFullDto patchCancelUserIdEvent(Long userId, Long eventId) throws ObjectNotFoundException, RequestErrorException, ConditionsOperationNotMetException;
}
