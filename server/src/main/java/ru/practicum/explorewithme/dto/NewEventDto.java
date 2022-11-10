    package ru.practicum.explorewithme.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.*;
    import ru.practicum.explorewithme.model.Location;
    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.NotNull;
    import javax.validation.constraints.Size;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewEventDto {

        @NotBlank
        @Size(min = 20, max = 2000)
        private String annotation;
        @NotNull
        private Long category;
        @NotBlank
        @Size(min = 20, max = 7000)
        private String description;
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        @NotNull
        private Location location;
        private boolean paid;
        @Builder.Default
        private Long participantLimit = 0L;
        @Builder.Default
        private Boolean requestModeration = true;
        @NotBlank
        @Size(min = 3, max = 120)
        private String title;
    }
