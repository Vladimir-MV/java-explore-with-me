    package ru.practicum.explorewithme.admin.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.dto.UserDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.UserMapper;
    import ru.practicum.explorewithme.dto.NewUserRequest;
    import ru.practicum.explorewithme.model.User;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import ru.practicum.explorewithme.repository.UserRepository;
    import java.util.List;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class AdminUserServiceImpl implements AdminUserService {
        private final UserRepository userRepository;
        @Transactional(readOnly = true)
        @Override
        public List<UserDto> getUsersByIds(List<Long> ids, Integer from, Integer size)
                throws ObjectNotFoundException {
            final Pageable pageable = FromSizeRequest.of(from, size);
            List<User> listUsers;
            if (ids.isEmpty()) {
                listUsers = userRepository.findAll(pageable).getContent();
            } else {
                listUsers = userRepository.findById(ids, pageable).getContent();
            }
            log.info("Найден список пользователей ids {}", ids);
            return UserMapper.toListUserDto(listUsers);
        }

        @Transactional
        @Override
        public UserDto createUser(NewUserRequest userRequest) {
            User user = UserMapper.toUser(userRequest);
            userRepository.saveAndFlush(user);
            log.info("Добавлен новый пользователь name {}", user.getName());
            return UserMapper.toUserDto(user);
        }

        @Transactional
        @Override
        public void deleteUserById(Long userId) {
            log.info("Удален пользователь userId={}", userId);
            userRepository.deleteById(userId);
        }
    }
