    package ru.practicum.explorewithme.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import ru.practicum.explorewithme.model.State;
    import java.time.LocalDateTime;
    import java.util.Set;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class EventFullDto {

        private String annotation;
        private CategoryDto category;
        private Long confirmedRequests;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdOn;
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        private Long id;
        private UserShortDto initiator;
        private LocationDto location;
        private boolean paid;
        private Long participantLimit;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime publishedOn;
        private Boolean requestModeration;
        private State state;
        private String title;
        private Long views;
        private Set<LocationGroupDto> locationGroupDtos;

    }
