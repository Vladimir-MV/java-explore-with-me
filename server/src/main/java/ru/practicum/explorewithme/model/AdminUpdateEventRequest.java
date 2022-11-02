    package ru.practicum.explorewithme.model;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class AdminUpdateEventRequest {
        private String annotation;
        private Long category;
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        private Location location;
        private Boolean paid;
        private Long participantLimit;
        private Boolean requestModeration;
        private String title;
    }
