    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.mapper.CategoryMapper;
    import ru.practicum.explorewithme.model.Category;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import java.util.List;
    import java.util.Optional;

    @Slf4j
    @Service
    public class PublicCategoryServiceImpl implements PublicCategoryService{
        private CategoryRepository categoryRepository;

        @Autowired
        public PublicCategoryServiceImpl (CategoryRepository categoryRepository) {
            this.categoryRepository = categoryRepository;
        }

        @Override
        public List<CategoryDto> getCategories(Integer from, Integer size) throws MethodExceptions {
            final Pageable pageable = FromSizeRequest.of(from, size);
            List<Category> listCategory = categoryRepository.findAllCategory(pageable).getContent();
            if (!listCategory.isEmpty()) {
                return CategoryMapper.toListCategoryDto(listCategory);
            }
            throw new MethodExceptions(String.format("Compilation with id={} was not found."),
                    404, "The required object was not found.");
        }

        @Override
        public CategoryDto getCategoryById(Optional<Long> catId) throws MethodExceptions {
            if (catId.isPresent()) {
                Optional<Category> category = categoryRepository.findCategoryById(catId.get());
                if (category.isPresent()) {
                    return CategoryMapper.toCategoryDto(category.get());
                } else {
                    throw new MethodExceptions(String.format("Compilation with id={} was not found.", catId),
                        404, "The required object was not found.");
                }
            } else {
                throw new MethodExceptions(String.format("Only pending or canceled events can be changed"),
                        400, "For the requested operation the conditions are not met.");
            }
        }
    }
