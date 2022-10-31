    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class AdminUpdateEventRequest {
        private String annotation;
        private Long category;
        private String description;
        private String eventDate;
        private Location location;
        private Boolean paid;
        private Long participantLimit;
        private Boolean requestModeration;
        private String title;
    }
