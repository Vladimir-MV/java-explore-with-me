package ru.practicum.explorewithme.privaterequest.service;

import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.exceptions.MethodExceptions;

import java.util.List;
import java.util.Optional;

public interface PrivateUserRequestService {
    List<ParticipationRequestDto> getUserUserRequests(Optional<Long> userId) throws MethodExceptions;

    ParticipationRequestDto createUserRequest(Optional<Long> userId, Optional<Long> eventId) throws MethodExceptions;

    ParticipationRequestDto cancelUserRequestById(Optional<Long> userId, Optional<Long> requestId) throws MethodExceptions;
}
