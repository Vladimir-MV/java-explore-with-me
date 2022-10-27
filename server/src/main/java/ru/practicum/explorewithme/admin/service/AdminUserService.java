package ru.practicum.explorewithme.admin.service;

import ru.practicum.explorewithme.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface AdminUserService {
    List<UserDto> getUsersByIds(Long[] ids, Integer from, Integer size);

    UserDto createUser(Optional<UserDto> userDto);

    void deleteUserIdById(Optional<Long> userId);
}
