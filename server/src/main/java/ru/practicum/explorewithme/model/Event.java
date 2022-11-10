    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.persistence.*;
    import java.time.LocalDateTime;
    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Table(name = "events")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Event {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;
        @Column(name = "annotation", nullable = false)
        private String annotation;
        @ManyToOne
        @JoinColumn(name="category_id")
        private Category category;
        @Column(name="confirmed_requests")
        private Long confirmedRequests;
        @Column(name="created_on")
        private LocalDateTime createdOn;
        @Column(name = "description", nullable = false)
        private String description;
        @Column(name="event_date", nullable = false)
        private LocalDateTime eventDate;
        @ManyToOne
        @JoinColumn(name="initiator_id")
        private User initiator;
        @OneToOne
        @JoinColumn(name="location_id", nullable = false)
        private Location location;
        @Column(name = "is_paid", nullable = false)
        private boolean paid;
        @Column(name="participant_limit", nullable = false)
        private Long participantLimit;
        @Column(name="published_on")
        private LocalDateTime publishedOn;
        @Column(name = "is_request_moderation", nullable = false)
        private Boolean requestModeration;
        @Enumerated(EnumType.STRING)
        @Column(name = "state")
        private State state;
        @Column(name = "title", nullable = false)
        private String title;
        @Column
        private Long views;
        //Фича: Локация(группа) к которой относится событие.
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name="location_groups_events",
                joinColumns =@JoinColumn(name="event_id"),
                inverseJoinColumns = @JoinColumn(name="location_group_id"))
        private Set<LocationGroup> locationGroup = new HashSet<>();
    }
