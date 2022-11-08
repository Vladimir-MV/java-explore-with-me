    package ru.practicum.explorewithme.repository;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import ru.practicum.explorewithme.model.Compilation;
    import java.util.Optional;

    public interface CompilationRepository extends JpaRepository<Compilation, Long> {

//        @Query("select c from Compilation c where c.id = ?1")
//        Optional<Compilation> findCompilationById(Long id);

        @Query("select c from Compilation c where c.pinned = ?1")
        Page<Compilation> findCompilationByPinned(Boolean pinned, Pageable pageable);

        @Query("select c from Compilation c ")
        Page<Compilation> findCompilation(Pageable pageable);
    }
