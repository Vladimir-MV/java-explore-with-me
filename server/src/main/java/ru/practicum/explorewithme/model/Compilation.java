    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import javax.persistence.*;
    import java.util.ArrayList;
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
        @ManyToMany(cascade=CascadeType.ALL)
        @JoinTable(name="compilations_events",
        joinColumns =@JoinColumn(name="compilation_id"),
        inverseJoinColumns = @JoinColumn(name="event_id"))
        private List<Event> events = new ArrayList<>();
        @Column(name="is_pinned")
        private Boolean pinned;
        private String title;



    }
