package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Compilation;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.id = ?1")
    Optional<List<Category>> findCategoryById(Long[] id);

    @Query("select c from Category c ")
    Page<Category> findAllCategory(Pageable pageable);


}
