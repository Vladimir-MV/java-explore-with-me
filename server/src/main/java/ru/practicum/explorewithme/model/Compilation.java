    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import javax.persistence.*;
    import java.util.List;

    @Entity
    @Table(name = "compilations")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Compilation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Boolean pinned;
        private String title;
        @ManyToMany(fetch=FetchType.EAGER,
                cascade=CascadeType.ALL)
        @JoinColumn(name="event_id")
        private List<Event> event;
    }
