    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
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
        public List<CategoryDto> getCategories(Integer from, Integer size) throws ObjectNotFoundException {
            final Pageable pageable = FromSizeRequest.of(from, size);
            List<Category> listCategory = categoryRepository.findAll(pageable).getContent();
            if (listCategory.size() > 0) {
                return CategoryMapper.toListCategoryDto(listCategory);
            }
            throw new ObjectNotFoundException(String.format("Compilation was not found."));
        }

        @Override
        public CategoryDto getCategoryById(Long catId) throws RequestErrorException, ObjectNotFoundException {
           // if (catId.isPresent()) {
                Category category = categoryRepository.findById(catId).orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", catId)));
                //if (category.isPresent()) {
                return CategoryMapper.toCategoryDto(category);

//            } else {
//                throw new RequestErrorException();
//            }
        }
    }
