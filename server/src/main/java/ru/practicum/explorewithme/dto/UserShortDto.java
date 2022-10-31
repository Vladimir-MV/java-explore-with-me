    package ru.practicum.explorewithme.dto;

    import lombok.*;

    import javax.validation.constraints.NotNull;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserShortDto {
        @NotNull
        private Long id;
        @NotNull
        private String name;
    }
