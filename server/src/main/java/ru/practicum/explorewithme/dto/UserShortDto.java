    package ru.practicum.explorewithme.dto;

    import lombok.*;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserShortDto {
        @NonNull
        private Long id;
        @NonNull
        private String name;
    }
