    package ru.practicum.explorewithme.dto;


    import lombok.*;

    import javax.validation.constraints.Email;
    import javax.validation.constraints.NotNull;

    @NonNull
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDto {
        private Long id;
        @NotNull
        @Email
        private String email;
        @NotNull
        private String name;
    }
