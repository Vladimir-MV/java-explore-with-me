    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.CategoryMapper;
    import ru.practicum.explorewithme.model.Category;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import java.util.List;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PublicCategoryServiceImpl implements PublicCategoryService {

        private final CategoryRepository categoryRepository;

        @Transactional(readOnly = true)
        @Override
        public List<CategoryDto> getCategories(Integer from, Integer size)
                throws ObjectNotFoundException {
            final Pageable pageable = FromSizeRequest.of(from, size);
            List<Category> listCategory = categoryRepository.findAll(pageable).getContent();
            if (listCategory.size() > 0) {
                return CategoryMapper.toListCategoryDto(listCategory);
            }
            throw new ObjectNotFoundException("Объект не найден. ",
                    String.format("Compilation was not found."));
        }

        @Transactional(readOnly = true)
        @Override
        public CategoryDto getCategoryById(Long catId) throws ObjectNotFoundException {
                Category category = categoryRepository.findById(catId).orElseThrow(
                        () -> new ObjectNotFoundException("Объект не найден. ",
                                String.format("Compilation with id={} was not found.", catId)));
                return CategoryMapper.toCategoryDto(category);
        }
    }
