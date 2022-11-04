    package ru.practicum.explorewithme.admin.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.UserDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.UserMapper;
    import ru.practicum.explorewithme.model.NewUserRequest;
    import ru.practicum.explorewithme.model.User;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import ru.practicum.explorewithme.repository.UserRepository;
    import java.util.List;

    @Slf4j
    @Service
    public class AdminUserServiceImpl implements AdminUserService{
        private UserRepository userRepository;
        @Autowired
        public AdminUserServiceImpl (UserRepository userRepository) {
            this.userRepository = userRepository;
        }
        @Override
        public List<UserDto> getUsersByIds(List<Long> ids, Integer from, Integer size) {
            final Pageable pageable = FromSizeRequest.of(from, size);
            List<User> listUsers = userRepository.searchUsersListById(ids, pageable).getContent();
            return UserMapper.toListUserDto(listUsers);
        }

        @Override
        public UserDto createUser(NewUserRequest userRequest) {
            User user = UserMapper.toUser(userRequest);
            userRepository.saveAndFlush(user);
            log.info("Добавлен новый пользователь name {}", user.getName());
            return UserMapper.toUserDto(user);
        }

        @Override
        public void deleteUserById(Long userId) throws ObjectNotFoundException {
            User user = userRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("User with id={} was not found.", userId)));
            log.info("Удален пользователь userId={}", userId);
            userRepository.findById(userId);
            userRepository.deleteById(user.getId());
        }
    }
