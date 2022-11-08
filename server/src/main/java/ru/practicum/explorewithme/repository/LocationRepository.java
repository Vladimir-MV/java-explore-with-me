    package ru.practicum.explorewithme.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.model.Location;
    import ru.practicum.explorewithme.model.State;

    import java.util.Optional;

    public interface LocationRepository extends JpaRepository<Location, Long> {
//        @Query("select e from Event e where e.id = ?1 and e.state = ?2 ")
        Optional<Location> findByLatAndLon(float lat, float lon);

    }
