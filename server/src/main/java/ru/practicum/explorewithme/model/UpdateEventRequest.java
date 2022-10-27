    package ru.practicum.explorewithme.model;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.Size;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UpdateEventRequest {
        @Size(min=20, max=2000)
        private String annotation;
        private Long category;
        @Size(min=20, max=7000)
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        @NotBlank
        private Long eventId;
        private Boolean paid;
        private Integer participantLimit;
        @Size(min=3, max=120)
        private String title;

    }
