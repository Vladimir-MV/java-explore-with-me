    package ru.practicum.explorewithme.privaterequest.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.ParticipationRequestMapper;
    import ru.practicum.explorewithme.model.*;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.RequestRepository;
    import ru.practicum.explorewithme.repository.UserRepository;

    import java.time.LocalDateTime;
    import java.util.List;

    @Slf4j
    @Service
    public class PrivateUserRequestServiceImpl implements PrivateUserRequestService {
        private UserRepository userRepository;
        private CategoryRepository categoryRepository;
        private EventRepository eventRepository;
        private RequestRepository requestRepository;
        @Autowired
        public PrivateUserRequestServiceImpl (EventRepository eventRepository, UserRepository userRepository,
                                            RequestRepository requestRepository, CategoryRepository categoryRepository) {
            this.eventRepository = eventRepository;
            this.userRepository = userRepository;
            this.categoryRepository = categoryRepository;
            this.requestRepository = requestRepository;
        }
        private Event eventValidation (Long eventId) throws ObjectNotFoundException, RequestErrorException {
            Event event = eventRepository.findById(eventId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId)));
            return event;
        }
        private User userValidation (Long userId) throws ObjectNotFoundException, RequestErrorException {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("User with id={} was not found.", userId)));
            return user;
        }

        @Override
        public List<ParticipationRequestDto> getUserRequests(Long userId) throws ObjectNotFoundException, RequestErrorException {
            userValidation(userId);
            List<ParticipationRequest> listRequest = requestRepository.findAllRequestUserById(userId).orElseThrow(
                    () -> new ObjectNotFoundException(
                            String.format("ParticipationRequest list with userId={} was not found.", userId)));
            log.info("Получение информации о заявках текущего пользователя на участие в чужих событиях userId={}", userId);
            return ParticipationRequestMapper.toListParticipationRequestDto(listRequest);
        }

        @Override
        public ParticipationRequestDto createUserRequest(Long userId, Long eventId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            User user = userValidation(userId);
            Event event = eventValidation(eventId);
            if (event.getInitiator().getId().equals(userId))
                throw new ConditionsOperationNotMetException();
            if (event.getState() != State.PUBLISHED)
                throw new ConditionsOperationNotMetException();
            if (event.getParticipantLimit() != 0 && (event.getParticipantLimit() - event.getConfirmedRequests() <=0))
                throw new ConditionsOperationNotMetException();
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

        @Override
        public ParticipationRequestDto cancelUserRequestById(Long userId, Long requestId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            userValidation(userId);
            ParticipationRequest participationRequest = requestRepository.findRequestById(requestId).orElseThrow(
                    () -> new ObjectNotFoundException(
                            String.format("ParticipationRequest with reqId={} was not found.", requestId)));
            if (participationRequest.getRequester().getId() != userId)
                throw new ConditionsOperationNotMetException();
            participationRequest.setStatus(Status.CANCELED);
            requestRepository.save(participationRequest);
            log.info("Отмена своего запроса requestId={} на участие в событии userId={}", requestId, userId);
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }
    }
