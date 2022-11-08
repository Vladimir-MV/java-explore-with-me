    package ru.practicum.explorewithme.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;
    import ru.practicum.explorewithme.model.LocationGroup;


    public interface LocationGroupRepository extends JpaRepository<LocationGroup, Long> {

        // Фича: ссылка на хранимую функцию анотацией @Query
        @Query(value = "distance(:lat1, :lon1, :lat2, :lon2);", nativeQuery = true)
        float distanceBetweenLocations (
            @Param("lat1") float lat1, @Param("lon1") float lon1, @Param("lat2") float lat2, @Param("lon2") float lon2);
    }
