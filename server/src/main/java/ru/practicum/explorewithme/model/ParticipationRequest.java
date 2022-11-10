    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import javax.persistence.*;
    import java.time.LocalDateTime;
    @Entity
    @Table(name = "requests")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class ParticipationRequest {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;
        private LocalDateTime created;
        @ManyToOne
        @JoinColumn(name="event_id", nullable = false)
        private Event event;
        @ManyToOne
        @JoinColumn(name="requester_id", nullable = false)
        private User requester;
        @JoinColumn(name = "status", nullable = false)
        @Enumerated(EnumType.STRING)
        private Status status;
    }
