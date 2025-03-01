    package ru.practicum.explorewithme.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.model.LocationGroup;
    import java.util.Optional;
    import java.util.Set;

    public interface LocationGroupRepository extends JpaRepository<LocationGroup, Long> {


        // Фича: ссылка на хранимую функцию анотацией @Query
        @Query(value = "select * from distance(?1, ?2, ?3, ?4)", nativeQuery = true)
        float distanceBetweenLocations(float lat1, float lon1, float lat2, float lon2);

        @Query("select l.eventGroup from LocationGroup l where l.id = ?1 ORDER BY l.id asc")
        Optional<Set<Event>> findEventsByLocationId(Long id);

        Optional<LocationGroup> findByLatAndLon(float lat, float lon);
    }
