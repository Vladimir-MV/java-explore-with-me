    package ru.practicum.explorewithme.privaterequest.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.ParticipationRequestMapper;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.model.ParticipationRequest;
    import ru.practicum.explorewithme.model.State;
    import ru.practicum.explorewithme.model.User;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.RequestRepository;
    import ru.practicum.explorewithme.repository.UserRepository;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Optional;

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

        private Event eventValidation (Optional<Long> eventId) throws ObjectNotFoundException, RequestErrorException {
            if (!eventId.isPresent()) throw new RequestErrorException();
            Optional<Event> event = eventRepository.findEventById(eventId.get());
            if (!event.isPresent())
                throw new ObjectNotFoundException(String.format("Event with id={} was not found.", event.get()));
            return event.get();
        }
        private User userValidation (Optional<Long> userId) throws ObjectNotFoundException, RequestErrorException {
            if (!userId.isPresent()) throw new RequestErrorException();
            Optional<User> user = userRepository.findUserById(userId.get());
            if (!user.isPresent())
                throw new ObjectNotFoundException(String.format("User with id={} was not found.", userId.get()));
            return user.get() ;
        }

        @Override
        public List<ParticipationRequestDto> getUserUserRequests(Optional<Long> userId) throws ObjectNotFoundException, RequestErrorException {
            userValidation(userId);
            Optional<List<ParticipationRequest>> listRequest = requestRepository.findAllRequestUserById(userId.get());
            if (!listRequest.isPresent())
                throw new ObjectNotFoundException(String.format("ParticipationRequest list with userId={} was not found.",
                        userId.get()));
            log.info("Получение информации о заявках текущего пользователя на участие в чужих событиях userId={}", userId.get());
            return ParticipationRequestMapper.toListParticipationRequestDto(listRequest.get());
        }

        @Override
        public ParticipationRequestDto createUserRequest(Optional<Long> userId, Optional<Long> eventId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            User user = userValidation(userId);
            Event event = eventValidation(eventId);
            if (event.getInitiator().getId().equals(userId.get()))
                throw new ConditionsOperationNotMetException();
            Optional<ParticipationRequest> participationRequestOp =
                    requestRepository.findRequestUserByIdAndEventById(userId.get(), eventId.get());
            if (participationRequestOp.isPresent())
                throw new ConditionsOperationNotMetException();
            if (!event.getState().equals(State.PUBLISHED))
                throw new ConditionsOperationNotMetException();
            if (event.getParticipantLimit().equals(event.getConfirmedRequests()))
                throw new ConditionsOperationNotMetException();
            ParticipationRequest participationRequest = new ParticipationRequest();
            if (event.getRequestModeration() == false) {
                participationRequest.setStatus("CONFIRMED");
            } else {
                participationRequest.setStatus("PENDING");
            }
            participationRequest.setRequester(user);
            participationRequest.setCreated(LocalDateTime.now());
            participationRequest.setEvent(event);
            requestRepository.save(participationRequest);
            log.info("Добавление запроса от текущего пользователя  userId={} на участие в событии", userId.get());
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }

        @Override
        public ParticipationRequestDto cancelUserRequestById(Optional<Long> userId, Optional<Long> requestId)
                throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            userValidation(userId);
            Optional<ParticipationRequest> participationRequestOp = requestRepository.findRequestById(requestId.get());
            if (!participationRequestOp.isPresent())
                throw new ObjectNotFoundException(String.format("ParticipationRequest with reqId={} was not found.", requestId.get()));
            ParticipationRequest participationRequest = participationRequestOp.get();
            if (!participationRequest.getRequester().getId().equals(userId.get()))
                throw new ConditionsOperationNotMetException();
            requestRepository.delete(participationRequest);
            log.info("Отмена своего запроса на участие в событии userId={}", userId.get());
            return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
        }
    }
