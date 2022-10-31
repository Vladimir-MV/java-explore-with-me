    package ru.practicum.explorewithme.admin.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.UserDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.UserMapper;
    import ru.practicum.explorewithme.model.NewUserRequest;
    import ru.practicum.explorewithme.model.User;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import ru.practicum.explorewithme.repository.UserRepository;
    import java.util.List;
    import java.util.Optional;

    @Slf4j
    @Service
    public class AdminUserServiceImpl implements AdminUserService{
        private UserRepository userRepository;
        @Autowired
        public AdminUserServiceImpl (UserRepository userRepository) {
            this.userRepository = userRepository;
        }
        @Override
        public List<UserDto> getUsersByIds(Optional<List<Long>> ids, Integer from, Integer size) throws ConditionsOperationNotMetException, ObjectNotFoundException {
            final Pageable pageable = FromSizeRequest.of(from, size);
            if (!ids.isPresent()) throw new ConditionsOperationNotMetException();
            List<User> listUsers = userRepository.searchUsersListById(ids.get(), pageable).getContent();
            if (listUsers.isEmpty()) throw new ObjectNotFoundException(String.format("Users with ids={} was not found.", ids.get()));
            return UserMapper.toListUserDto(listUsers);
        }

        @Override
        public UserDto createUser(Optional<NewUserRequest> userRequest) throws ConditionsOperationNotMetException {
            if (!userRequest.isPresent()) throw new ConditionsOperationNotMetException();
            User user = UserMapper.toUser(userRequest.get());
            userRepository.save(user);
            log.info("Добавлен новый пользователь userId={}", user.getId());
            return UserMapper.toUserDto(user);
        }

        @Override
        public void deleteUserIdById(Optional<Long> userId) throws ConditionsOperationNotMetException, ObjectNotFoundException {
            if (!userId.isPresent()) throw new ConditionsOperationNotMetException();
            Optional<User> user = userRepository.findUserById(userId.get());
            if (user.isPresent()) throw new ObjectNotFoundException(String.format("User with id={} was not found.", userId.get()));
            userRepository.delete(user.get());
            log.info("Удален пользователь userId={}", userId.get());
        }
    }
