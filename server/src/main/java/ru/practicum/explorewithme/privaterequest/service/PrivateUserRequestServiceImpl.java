    package ru.practicum.explorewithme.privaterequest.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.ParticipationRequestMapper;
    import ru.practicum.explorewithme.model.*;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.RequestRepository;
    import ru.practicum.explorewithme.repository.UserRepository;

    import java.time.LocalDateTime;
    import java.util.List;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PrivateUserRequestServiceImpl implements PrivateUserRequestService {

        private final UserRepository userRepository;
        private final EventRepository eventRepository;
        private final RequestRepository requestRepository;
        private Event eventValidation(Long eventId) throws ObjectNotFoundException {
            return eventRepository.findById(eventId).orElseThrow(() ->
                    new ObjectNotFoundException("Объект не найден. ",
                        String.format("Event with id={} was not found.", eventId)));
        }

        private User userValidation(Long userId) throws ObjectNotFoundException {
            return userRepository.findById(userId).orElseThrow(() ->
                    new ObjectNotFoundException("Объект не найден. ",
                        String.format("User with id={} was not found.", userId)));
        }

        @Transactional(readOnly = true)
        @Override
        public List<ParticipationRequestDto> getUserRequests(Long userId) throws ObjectNotFoundException {
            List<ParticipationRequest> listRequest = requestRepository
                    .findAllRequestUserById(userId).orElseThrow(() ->
                            new ObjectNotFoundException("Объект не найден. ",
                                String.format("ParticipationRequest list with userId={} was not found.", userId)));
            log.info("Получение информации о заявках текущего пользователя на участие в чужих событиях userId={}", userId);
            return ParticipationRequestMapper.toListParticipationRequestDto(listRequest);
        }

        @Transactional
        @Override
        public ParticipationRequestDto createUserRequest(Long userId, Long eventId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException {
            if (requestRepository.findRequestByUserIdAndEventId(userId, eventId).isPresent()) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для совершения операции", "requestRepository");
            }
            User user = userValidation(userId);
            Event event = eventValidation(eventId);
            if (event.getInitiator().getId().equals(userId)) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для" +
                        " совершения операции", "Initiator");
            }
            if (event.getState() != State.PUBLISHED) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для" +
                        " совершения операции", "State");
            }
            if (event.getParticipantLimit() != 0
                    && (event.getParticipantLimit() - event.getConfirmedRequests() <= 0)) {
                throw new ConditionsOperationNotMetException("Не выполнены условия для" +
                        " совершения операции", "ParticipantLimit");
            }
            ParticipationRequest participationRequest = new ParticipationRequest();
            if (event.getRequestModeration() == false) {
                participationRequest.setStatus(Status.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                eventRepository.save(event);
            } else {
                participationRequest.setStatus(Status.PENDING);
            }

            participationRequest.setRequester(user);
            participationRequest.setCreated(LocalDateTime.now());
            participationRequest.setEvent(event);
            requestRepository.save(participationRequest);
            log.info("Добавление запроса от текущего пользователя  userId={} на участие в событии", userId);
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }

        @Transactional
        @Override
        public ParticipationRequestDto cancelUserRequestById(Long userId, Long requestId)
                throws ObjectNotFoundException {
            ParticipationRequest participationRequest = requestRepository
                    .findRequestById(userId, requestId).orElseThrow(() ->
                            new ObjectNotFoundException("Объект не найден. ",
                                String.format("ParticipationRequest with reqId={} was not found.", requestId)));

            participationRequest.setStatus(Status.CANCELED);
            requestRepository.save(participationRequest);
            log.info("Отмена своего запроса requestId={} на участие в событии userId={}", requestId, userId);
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }
    }
