    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.persistence.*;

    @Entity
    @Table(name = "locations")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Location {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;
        @Column(name = "lat", nullable = false)
        private float lat;
        @Column(name = "lon", nullable = false)
        private float lon;
    }
