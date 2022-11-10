    package ru.practicum.explorewithme.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import ru.practicum.explorewithme.model.LocationGroup;

    public interface LocationGroupRepository extends JpaRepository<LocationGroup, Long> {

        // Фича: ссылка на хранимую функцию анотацией @Query
        @Query(value = "select * from distance(?1, ?2, ?3, ?4)", nativeQuery = true)
        float distanceBetweenLocations (float lat1, float lon1, float lat2, float lon2);

    }
