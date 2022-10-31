    package ru.practicum.explorewithme.model;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.persistence.*;
    import java.time.LocalDateTime;
    @Entity
    @Table(name = "events")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Event {

        public Event(String annotation, String description, LocalDateTime eventDate, Location location,
               Boolean paid, Integer participantLimit, Boolean requestModeration, String title) {
                this.annotation= annotation;
                this.description = description;
                this.eventDate = eventDate;
                this.location = location;
                this.paid= paid;
                this.participantLimit = participantLimit;
                this.requestModeration = requestModeration;
                this.title = title;

        }
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String annotation;
        @ManyToOne(fetch=FetchType.EAGER,
                cascade = CascadeType.ALL)
        @JoinColumn(name="category_id")
        private Category category;
        @Column(name="confirmed_requests")
        private Long confirmedRequests;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Column(name="created_on")
        private LocalDateTime createdOn;
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Column(name="event_date")
        private LocalDateTime eventDate;
        @ManyToOne(fetch=FetchType.EAGER,
                cascade=CascadeType.ALL)
        @JoinColumn(name="initiator_id")
        private User initiator;
        @OneToOne(fetch=FetchType.EAGER,
                cascade=CascadeType.ALL)
        @JoinColumn(name="location_id")
        private Location location;
        @Column(name = "is_paid")
        private Boolean paid;
        @Column(name="participant_limit")
        private Integer participantLimit;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Column(name="published_on")
        private LocalDateTime publishedOn;
        @Column(name = "is_request_moderation")
        private Boolean requestModeration;
        @Enumerated(EnumType.STRING)
        private State state;
        private String title;
        private Long views;

    }
