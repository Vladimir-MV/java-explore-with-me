    package ru.practicum.explorewithme.privaterequest.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.privaterequest.service.PrivateUserRequestService;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/users/{userId}/requests")
    @Slf4j
    @RequiredArgsConstructor
    public class PrivateUserRequestController {


        private final PrivateUserRequestService privateUserRequestService;

        @GetMapping
        public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
            log.info("privateUserRequests get user request by userId={}", userId);
            return privateUserRequestService.getUserRequests(userId);
        }

        @PostMapping
        public ParticipationRequestDto addUserRequest(@PathVariable Long userId,
                                                      @RequestParam Long eventId) {
            log.info("privateUserRequest, create request userId={}, eventId={}", userId, eventId);
            return privateUserRequestService.createUserRequest(userId, eventId);
        }

        @PatchMapping("/{requestId}/cancel")
        public ParticipationRequestDto updateCancelUserRequest(@PathVariable Long userId,
                                                               @PathVariable Long requestId) {
            log.info("privateCancelUserRequest, cancel user request userId={}, requestId={}",
                userId, requestId);
            return privateUserRequestService.cancelUserRequestById(userId, requestId);
        }
    }
