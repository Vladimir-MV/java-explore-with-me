    package ru.practicum.explorewithme.privaterequest.service;

    import ru.practicum.explorewithme.dto.ParticipationRequestDto;

    import java.util.List;

    public interface PrivateUserRequestService {

        List<ParticipationRequestDto> getUserRequests(Long userId);

        ParticipationRequestDto createUserRequest(Long userId, Long eventId);

        ParticipationRequestDto cancelUserRequestById(Long userId, Long requestId);
    }
