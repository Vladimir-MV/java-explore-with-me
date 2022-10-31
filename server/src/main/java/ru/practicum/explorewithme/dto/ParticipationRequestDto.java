    package ru.practicum.explorewithme.dto;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import ru.practicum.explorewithme.model.Status;

    import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class ParticipationRequestDto {
        private LocalDateTime created;
        private Long event;
        private Long id;
        private Long requester;
        private Status status;

    }
