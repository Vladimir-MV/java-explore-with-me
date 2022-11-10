    package ru.practicum.explorewithme.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import java.time.LocalDateTime;

    import java.util.Set;

    @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public class EventShortLocationDto {

                private String annotation;
                private CategoryDto category;
                private Long confirmedRequests;
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                private LocalDateTime eventDate;
                private Long id;
                private UserShortDto initiator;
                private boolean paid;
                private String title;
                private Long views;
                private Set<LocationGroupDto> locationGroup;
        }
