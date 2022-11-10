    package ru.practicum.explorewithme.privaterequest.service;

    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;

    import java.util.List;

    public interface PrivateUserRequestService {

        List<ParticipationRequestDto> getUserRequests(Long userId)
                throws ObjectNotFoundException, RequestErrorException;

        ParticipationRequestDto createUserRequest(Long userId, Long eventId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException;

        ParticipationRequestDto cancelUserRequestById(Long userId, Long requestId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException;
    }
