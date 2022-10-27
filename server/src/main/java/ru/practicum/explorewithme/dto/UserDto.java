    package ru.practicum.explorewithme.dto;


    import lombok.*;

    //import javax.validation.constraints.NotNull;

    @NonNull
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDto {
        private Long id;
        @NonNull
        private String email;
        @NonNull
        private String name;
    }
