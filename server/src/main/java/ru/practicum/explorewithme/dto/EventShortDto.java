    package ru.practicum.explorewithme.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.validation.constraints.NotNull;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class EventShortDto {
        @NotNull
        private String annotation;
        @NotNull
        private CategoryDto category;
        private Long confirmedRequests;
        @NotNull
        private LocalDateTime eventDate;
        private Long id;
        @NotNull
        private UserShortDto initiator;
        @NotNull
        private Boolean paid;
        @NotNull
        private String title;
        private Long views;

    }
