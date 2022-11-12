    package ru.practicum.explorewithme.repository;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import ru.practicum.explorewithme.model.User;
    import java.util.List;

    public interface UserRepository extends JpaRepository<User, Long> {


        @Query("select u from User u where u.id = ?1")
        Page<User> findById(List<Long> id, Pageable pageable);

    }
