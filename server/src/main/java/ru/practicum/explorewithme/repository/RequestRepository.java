    package ru.practicum.explorewithme.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import ru.practicum.explorewithme.model.ParticipationRequest;

    import java.util.List;
    import java.util.Optional;

    public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
        @Query("select r from ParticipationRequest r " +
                " JOIN Event e ON r.event.id = e.id" +
                " where e.id = ?1 and e.initiator.id = ?2 ")
        Optional<List<ParticipationRequest>> findRequestUserByIdAndEventById(Long eventId, Long userId);

        @Query("select r from ParticipationRequest r where r.requester.id = ?1")
        Optional<List<ParticipationRequest>> findAllRequestUserById(Long id);

        @Query("select r from ParticipationRequest r where r.requester.id = ?1 and r.event.id = ?2")
        Optional<ParticipationRequest> findRequestByUserIdAndEventId(Long userId, Long eventId);

        @Query("select r from ParticipationRequest r where r.requester.id = ?1 and r.id = ?2 and r.status <> 'CONFIRMED'")
        Optional<ParticipationRequest> findRequestById(Long userId, Long id);

    }
