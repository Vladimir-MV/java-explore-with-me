    package ru.practicum.explorewithme.dto;


    import lombok.*;

    import javax.validation.constraints.NotNull;

    @NonNull
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDto {
        private Long id;
        @NotNull
        private String email;
        @NotNull
        private String name;
    }
