    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import javax.persistence.*;
    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Table(name = "location_groups")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class LocationGroup {

        //Фича: Локация(группа) в радиусе которой происходит событие.
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        private Long id;
        @Column(nullable = false)
        private String name;
        @Column(nullable = false)
        private float lat;
        @Column(nullable = false)
        private float lon;
        @Column(nullable = false)
        private float radius;
        @Column
        private String description;
        @ManyToMany
        @JoinTable(name = "location_groups_events",
                joinColumns = @JoinColumn(name = "location_group_id"),
                inverseJoinColumns = @JoinColumn(name = "event_id"))
        private Set<Event> eventGroup = new HashSet<>();
    }
