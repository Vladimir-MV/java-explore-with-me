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
        public LocationGroup (String name, float lat, float lon, float radius) {
            this.name = name;
            this.lat = lat;
            this.lon = lon;
            this.radius = radius;
        }
        //Фича: Локация(группа) в радиусе которой происходит событие.
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;
        @Column(name = "name", nullable = false)
        private String name;
        @Column(name = "lat", nullable = false)
        private float lat;
        @Column(name = "lon", nullable = false)
        private float lon;
        @Column(name = "radius", nullable = false)
        private float radius;
        @Column
        private String description;
        @ManyToMany()
        @JoinTable(name="location_groups_events",
                joinColumns =@JoinColumn(name="location_group_id"),
                inverseJoinColumns = @JoinColumn(name="event_id"))
        private Set<Event> eventGroup = new HashSet<>();

    }
