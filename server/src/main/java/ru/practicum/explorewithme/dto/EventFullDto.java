    package ru.practicum.explorewithme.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import ru.practicum.explorewithme.model.Category;
    import ru.practicum.explorewithme.model.Location;
    import ru.practicum.explorewithme.model.State;

    import javax.validation.constraints.NotNull;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class EventFullDto {
        //@NotNull
        private String annotation;
       // @NotNull
        private CategoryDto category;
        private Long confirmedRequests;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdOn;
        private String description;
        //@NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        private Long id;
        private UserShortDto initiator;
        private LocationDto location;
       // @NotNull
        private Boolean paid;
        private Long participantLimit;
       // @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime publishedOn;
        private Boolean requestModeration;
        private State state;
        //@NotNull
        private String title;
        private Long views;

    }
