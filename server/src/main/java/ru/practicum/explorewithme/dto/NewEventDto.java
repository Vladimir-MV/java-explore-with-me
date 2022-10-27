    package ru.practicum.explorewithme.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import ru.practicum.explorewithme.model.Location;

    import javax.validation.constraints.NotBlank;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewEventDto {
        @NotBlank
        private String annotation;
        @NotBlank
        private Long category;
        @NotBlank
        private String description;
        @NotBlank
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        @NotBlank
        private Location location;
        private Boolean paid;
        private Integer participantLimit;
        private Boolean requestModeration;
        @NotBlank
        private String title;
    }
