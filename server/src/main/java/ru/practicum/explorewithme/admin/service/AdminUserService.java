    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.UserDto;
    import ru.practicum.explorewithme.dto.NewUserRequest;
    import java.util.List;

    public interface AdminUserService {


        List<UserDto> getUsersByIds(List<Long> ids, Integer from, Integer size);

        UserDto createUser(NewUserRequest userDto);

        void deleteUserById(Long userId);
    }
