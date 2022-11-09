    package ru.practicum.explorewithme.privaterequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.privaterequest.service.PrivateUserRequestService;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/users/{userId}/requests")
    @Slf4j
    @RequiredArgsConstructor
    public class PrivateUserRequestController {

        final private PrivateUserRequestService privateUserRequestService;

        @GetMapping
        public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId)
                throws ObjectNotFoundException, RequestErrorException {
            log.info("privateUserRequests get user request by userId={}", userId);
            return privateUserRequestService.getUserRequests(userId);
        }

        @PostMapping
        public ParticipationRequestDto addUserRequest(@PathVariable Long userId,
                                                      @RequestParam Long eventId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privateUserRequest, create request userId={}, eventId={}", userId, eventId);
            return privateUserRequestService.createUserRequest(userId, eventId);
        }

        @PatchMapping("/{requestId}/cancel")
        public ParticipationRequestDto updateCancelUserRequest(@PathVariable Long userId,
                                                               @PathVariable Long requestId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("privateCancelUserRequest, cancel user request userId={}, requestId={}",
                userId, requestId);
            return privateUserRequestService.cancelUserRequestById(userId, requestId);
        }

    }
