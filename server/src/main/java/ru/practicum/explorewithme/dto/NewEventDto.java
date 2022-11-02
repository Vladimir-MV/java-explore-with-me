    package ru.practicum.explorewithme.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.*;
    import org.springframework.boot.context.properties.bind.DefaultValue;
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
        @NotNull
        @Size(min=20, max=2000)
        private String annotation;
        @NotNull
        @NotBlank
        private Long category;
        @NotNull
        @Size(min=20, max=7000)
        private String description;
        @NotNull
        @NotBlank
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        @NotNull
        private Location location;
        @Builder.Default
        private Boolean paid = false;
        @Builder.Default
        private Long participantLimit = 0L;
        @Builder.Default
        private Boolean requestModeration = true;
        @NotNull
        @Size(min=3, max=120)
        private String title;
    }
