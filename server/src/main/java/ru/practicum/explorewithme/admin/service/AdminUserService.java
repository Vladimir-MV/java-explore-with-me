package ru.practicum.explorewithme.admin.service;

import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.model.NewUserRequest;

import java.util.List;
import java.util.Optional;

public interface AdminUserService {
    List<UserDto> getUsersByIds(Optional<List<Long>> ids, Integer from, Integer size)
            throws ConditionsOperationNotMetException, ObjectNotFoundException;

    UserDto createUser(Optional<NewUserRequest> userDto) throws ConditionsOperationNotMetException;

    void deleteUserIdById(Optional<Long> userId)
            throws ConditionsOperationNotMetException, ObjectNotFoundException;
}
