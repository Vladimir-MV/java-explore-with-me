package ru.practicum.explorewithme.admin.service;

import ru.practicum.explorewithme.dto.UserDto;

import java.util.List;
import java.util.Optional;

public class AdminUserServiceImpl implements AdminUserService{
    @Override
    public List<UserDto> getUsersByIds(Long[] ids, Integer from, Integer size) {
        return null;
    }

    @Override
    public UserDto createUser(Optional<UserDto> userDto) {
        return null;
    }

    @Override
    public void deleteUserIdById(Optional<Long> userId) {

    }
}
