    package ru.practicum.explorewithme.model;

    import com.fasterxml.jackson.annotation.JsonFormat;
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
        private Long id;
       // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime created;
        @ManyToOne(fetch=FetchType.EAGER,
                cascade=CascadeType.ALL)
        @JoinColumn(name="event_id")
        private Event event;
        @ManyToOne(fetch=FetchType.EAGER,
                cascade=CascadeType.ALL)
        @JoinColumn(name="requester_id")
        private User requester;
        @JoinColumn(name = "status")
        @Enumerated(EnumType.STRING)
        private Status status;
    }
