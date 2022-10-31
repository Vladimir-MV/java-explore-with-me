package ru.practicum.explorewithme.privaterequest.service;

import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;

import java.util.List;
import java.util.Optional;

public interface PrivateUserRequestService {
    List<ParticipationRequestDto> getUserUserRequests(Optional<Long> userId)
            throws ObjectNotFoundException, RequestErrorException;

    ParticipationRequestDto createUserRequest(Optional<Long> userId, Optional<Long> eventId)
            throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException;

    ParticipationRequestDto cancelUserRequestById(Optional<Long> userId, Optional<Long> requestId)
            throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException;
}
