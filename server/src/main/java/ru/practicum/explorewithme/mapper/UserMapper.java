    package ru.practicum.explorewithme.mapper;

    import ru.practicum.explorewithme.dto.UserDto;
    import ru.practicum.explorewithme.dto.UserShortDto;
    import ru.practicum.explorewithme.model.User;

    public class UserMapper {
        public static UserDto toUserDto (User user) {
            return new UserDto (
                    user.getId(),
                    user.getEmail(),
                    user.getName());
        }
        public static UserShortDto toUserShortDto (User user) {
            return new UserShortDto(
                    user.getId(),
                    user.getName());
        }


        public static User toUser (UserDto userDto) {
            return new User (
                    userDto.getId(),
                    userDto.getEmail(),
                    userDto.getName());
        }

    }
