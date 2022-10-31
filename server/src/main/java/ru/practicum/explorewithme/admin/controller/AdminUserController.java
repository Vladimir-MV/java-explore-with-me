    package ru.practicum.explorewithme.admin.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.practicum.explorewithme.admin.service.AdminUserService;
    import ru.practicum.explorewithme.admin.service.AdminUserServiceImpl;
    import ru.practicum.explorewithme.dto.UserDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.model.NewUserRequest;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/admin/users")
    @Slf4j
    public class AdminUserController {

        AdminUserService adminUserService;

        @Autowired
        public AdminUserController (AdminUserServiceImpl adminUserServiceImpl) {
            this.adminUserService = adminUserServiceImpl;
        }

        @GetMapping()
        public List<UserDto> adminGetUsers(
                @RequestParam(name = "ids") Optional<List<Long>> ids,
                @RequestParam(name = "from", defaultValue = "0") Integer from,
                @RequestParam(name = "size", defaultValue = "10") Integer size) throws ConditionsOperationNotMetException, ObjectNotFoundException {
            log.info("adminGetUsers, get users ids {}, from={}, size={}", ids, from, size);
            return adminUserService.getUsersByIds(ids, from, size);
        }

        @PostMapping()
        public UserDto adminPostUser(
            @RequestBody Optional<NewUserRequest> userRequest) throws ConditionsOperationNotMetException {
            log.info("adminPostUser, create user userDto {}", userRequest);
            return adminUserService.createUser(userRequest);
        }

        @DeleteMapping("/{userId}")
        public void adminDeleteUser(
            @PathVariable Optional<Long> userId) throws ConditionsOperationNotMetException, ObjectNotFoundException {
            log.info("adminDeleteUser, delete user userId={}", userId);
            adminUserService.deleteUserIdById(userId);
        }

    }


