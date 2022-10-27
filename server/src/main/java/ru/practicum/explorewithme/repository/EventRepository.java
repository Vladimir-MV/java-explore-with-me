package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

   // List<Item> findByOwner_IdOrderById(Long idUser);

    @Query("select e from Event e where e.id = ?1 and e.state = 'PUBLISHED' ")
    Optional<List<Event>> findByEvent_IdAndState(Long id);

    @Query("select e from Event e where e.initiator.id = ?1")
    Page<Event> findEventsByUserId(Long id, Pageable pageable);

    @Query("select e from Event e where e.id = ?1")
    Optional<Event> findEventsById(Long id);

//    @Query("SELECT i FROM Item i WHERE i.owner.id = ?1 ORDER BY i.id")
//    Page<Item> findAllItemsOwner(Long idUser, Pageable pageable);
//    @Query("select i from Item i " +
//            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
//            " or upper(i.description) like upper(concat('%', ?1, '%')) " +
//            "and i.available is true")
//    Page<Item> searchListItem(String text, Pageable pageable);
//    @Query("select i from Item i " +
//            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
//            " or upper(i.description) like upper(concat('%', ?1, '%')) " +
//            "and i.available is true")
//    List<Item> searchListItemText(String text);



}
