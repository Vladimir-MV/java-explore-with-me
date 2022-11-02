package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

   // List<Item> findByOwner_IdOrderById(Long idUser);

    @Query("select e from Event e where e.id = ?1 and e.state = 'PUBLISHED' ")
    Optional<List<Event>> findByEvent_IdAndState(Long id);

    @Query("select e from Event e where e.initiator.id = ?1")
    Page<Event> findEventsByUserId(Long id, Pageable pageable);

    @Query("select e from Event e where e.id = ?1")
    Optional<Event> findEventById(Long id);

    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            " or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' and e.category.id = ?2 and e.paid = ?3 " +
            "and e.eventDate > e.createdOn ORDER BY e.eventDate asc ")
    Page<Event> searchEventByEventDayAvailableFalseEndNull(String text, List<Long> category, Boolean paid,
                                   LocalDateTime rangeStart, Pageable pageable);
    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            " or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' and e.category.id = ?2 and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 ORDER BY e.eventDate asc")
    Page<Event> searchEventByEventDayAvailableFalseEndNotNull(String text, List<Long> category, Boolean paid,
                                   LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            " or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' and e.category.id = ?2 and e.paid = ?3 " +
            "and e.eventDate > e.createdOn ORDER BY e.eventDate asc")
    Page<Event> searchEventByEventDayAvailableTrueEndNull(String text, List<Long> category, Boolean paid,
                                                           LocalDateTime rangeStart, Pageable pageable);
    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            " or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' and e.category.id = ?2 and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 ORDER BY e.eventDate asc")
    Page<Event> searchEventByEventDayAvailableTrueEndNotNull(String text, List<Long> category, Boolean paid,
              LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

//    @Query("SELECT e FROM Event e " +
//            " WHERE e.initiator.id IN :users " +
//            " AND e.state IN :states " +
//            " AND e.category.id IN :categories " +
//            " AND e.eventDate BETWEEN :startDate AND :endDate "
//    )


    @Query("select e from Event e where e.initiator.id = ?1 and e.state = ?2 " +
            "and e.category.id = ?3 and e.eventDate between ?4 and ?5")
    Page<Event> searchEventsByAdminGetConditions(List<Long> usersId, List<State> states, List<Long> categoriesId,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
//    @Query("select e from Event e where e.initiator.id = ?1 and e.state = ?2 " +
//            "and e.eventDate between ?4 and ?5")
//    Page<Event> searchEventsByAdminGetConditionsWithOutCategory(Long[] usersId, String[] states, LocalDateTime rangeStart,
//                                   LocalDateTime rangeEnd, Pageable pageable);
    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            " or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' and e.category.id = ?2 and e.paid = ?3 " +
            "and e.eventDate > e.createdOn ORDER BY e.views")
    Page<Event> searchEventByViewsAvailableFalseEndNull(String text, List<Long> category, Boolean paid,
                                                           LocalDateTime rangeStart, Pageable pageable);
    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            " or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' and e.category.id = ?2 and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 ORDER BY e.views desc ")
    Page<Event> searchEventByViewsAvailableFalseEndNotNull(String text, List<Long> category, Boolean paid,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            " or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' and e.category.id = ?2 and e.paid = ?3 " +
            "and e.eventDate > e.createdOn ORDER BY e.views desc ")
    Page<Event> searchEventByViewsAvailableTrueEndNull(String text, List<Long> category, Boolean paid,
                                                          LocalDateTime rangeStart, Pageable pageable);
    @Query("select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            " or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.state = 'PUBLISHED' and e.category.id = ?2 and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 ORDER BY e.views desc ")
    Page<Event> searchEventByViewsAvailableTrueEndNotNull(String text, List<Long> category, Boolean paid,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
    @Query("select e from Event e where e.category.id = ?1")
    Optional<List<Event>> findEventByCategoryId(Long[] id);


}
