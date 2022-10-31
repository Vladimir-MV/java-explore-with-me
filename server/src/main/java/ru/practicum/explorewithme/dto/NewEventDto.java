    package ru.practicum.explorewithme.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.springframework.boot.context.properties.bind.DefaultValue;
    import ru.practicum.explorewithme.model.Location;

    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.Size;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewEventDto {
        @Size(min=20, max=2000)
        private String annotation;
        @NotBlank
        private Long category;
        @Size(min=20, max=7000)
        private String description;
        @NotBlank
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        @NotBlank
        private Location location;
        private Boolean paid;
        private Integer participantLimit;
        private Boolean requestModeration;
        @Size(min=3, max=120)
        private String title;
    }
