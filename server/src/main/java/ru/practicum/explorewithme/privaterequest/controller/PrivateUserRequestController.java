    package ru.practicum.explorewithme.privaterequest.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.privaterequest.service.PrivateUserRequestService;
    import ru.practicum.explorewithme.privaterequest.service.PrivateUserRequestServiceImpl;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/users/{userId}/requests")
    @Slf4j
    public class PrivateUserRequestController {

        PrivateUserRequestService privateUserRequestService;

        @Autowired
        public PrivateUserRequestController (PrivateUserRequestServiceImpl privateUserRequestServiceImpl) {
            this.privateUserRequestService = privateUserRequestServiceImpl;
        }

        @GetMapping()
        public List<ParticipationRequestDto> privateUserRequests(
                @PathVariable Long userId) throws ObjectNotFoundException, RequestErrorException {
            log.info("privateUserRequests get user request by userId={}", userId);
            return privateUserRequestService.getUserRequests(userId);
        }

        @PostMapping()
        public ParticipationRequestDto privateUserRequest(
                @PathVariable Long userId,
                @RequestParam Long eventId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privateUserRequest, create request userId={}, eventId={}", userId, eventId);
            return privateUserRequestService.createUserRequest(userId, eventId);
        }

        @PatchMapping("/{requestId}/cancel")
        public ParticipationRequestDto privateCancelUserRequest(
                @PathVariable Long userId,
                @PathVariable Long requestId) throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privateCancelUserRequest, cancel user request userId={}, requestId={}", userId, requestId);
            return privateUserRequestService.cancelUserRequestById(userId, requestId);
        }

    }
