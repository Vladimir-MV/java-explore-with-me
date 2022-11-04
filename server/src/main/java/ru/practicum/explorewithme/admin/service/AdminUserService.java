package ru.practicum.explorewithme.admin.service;

import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;
import ru.practicum.explorewithme.model.NewUserRequest;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getUsersByIds(List<Long> ids, Integer from, Integer size)
            throws ConditionsOperationNotMetException, ObjectNotFoundException;

    UserDto createUser(NewUserRequest userDto) throws ConditionsOperationNotMetException;

    void deleteUserById(Long userId)
            throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException;
}
