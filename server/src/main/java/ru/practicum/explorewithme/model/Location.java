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
        private Long id;
        private float lat;
        private float lon;
    }
