    package ru.practicum.explorewithme.admin.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminUserService;
    import ru.practicum.explorewithme.dto.UserDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.dto.NewUserRequest;

    import javax.validation.Valid;
    import javax.validation.constraints.Positive;
    import javax.validation.constraints.PositiveOrZero;
    import java.util.List;

    @RestController
    @RequestMapping(path = "/admin/users")
    @Slf4j
    @Validated
    @RequiredArgsConstructor
    public class AdminUserController {

        final private AdminUserService adminUserService;

        @GetMapping
        public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size)
                throws ConditionsOperationNotMetException, ObjectNotFoundException {
            log.info("adminGetUsers, get users ids {}, from={}, size={}", ids, from, size);
            return adminUserService.getUsersByIds(ids, from, size);
        }

        @PostMapping
        public UserDto addUser(@Valid @RequestBody NewUserRequest userRequest)
                throws ConditionsOperationNotMetException {
            log.info("adminPostUser, create user userDto {}", userRequest);
            return adminUserService.createUser(userRequest);
        }

        @DeleteMapping("/{userId}")
        public void deleteUser(@PathVariable Long userId)
            throws ConditionsOperationNotMetException, ObjectNotFoundException, RequestErrorException {
            log.info("adminDeleteUser, delete user userId={}", userId);
            adminUserService.deleteUserById(userId);
        }
    }


