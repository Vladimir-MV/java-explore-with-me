    package ru.practicum.explorewithme.admin.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.CategoryMapper;
    import ru.practicum.explorewithme.model.Category;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.EventRepository;

    import java.util.List;
    import java.util.Optional;
    @Slf4j
    @Service
    public class AdminCategoryServiceImpl implements AdminCategoryService {
        private EventRepository eventRepository;
        private CategoryRepository categoryRepository;
        @Autowired
        public AdminCategoryServiceImpl (CategoryRepository categoryRepository, EventRepository eventRepository) {
            this.eventRepository = eventRepository;
            this.categoryRepository = categoryRepository;
        }

        @Override
        public CategoryDto patchCategoryByIdAndName(Optional<CategoryDto> categoryDto) throws RequestErrorException {
            if (!categoryDto.isPresent()) throw new RequestErrorException();
            Optional<Category> categoryOp = categoryRepository.findCategoryById(categoryDto.get().getId());
            if (!categoryOp.isPresent()) throw new RequestErrorException();
            Category category = categoryOp.get();
            category.setName(categoryDto.get().getName());
            log.info("Изменение категории на категорию categoryDto={}", category.getName());
            return CategoryMapper.toCategoryDto(category);
        }

        @Override
        public CategoryDto createCategory(Optional<NewCategoryDto> newCategoryDto) throws RequestErrorException {
            if (!newCategoryDto.isPresent()) throw new RequestErrorException();
            Category category = CategoryMapper.toCategory(newCategoryDto.get());
            categoryRepository.save(category);
            log.info("Добавление новой категории category={}", category.getName());
            return CategoryMapper.toCategoryDto(category);
        }

        @Override
        public CategoryDto deleteCategoryById(Optional<Long> catId) throws RequestErrorException {
            if (!catId.isPresent()) throw new RequestErrorException();
            Optional<Category> category = categoryRepository.findCategoryById(catId.get());
            if (!category.isPresent()) throw new RequestErrorException();
            Optional<List<Event>> listEvent = eventRepository.findEventByCategoryId(catId.get());
            if (listEvent.isPresent()) throw new RequestErrorException();
            categoryRepository.delete(category.get());
            log.info("Удалена категория category={}", category.get().getName());
            return CategoryMapper.toCategoryDto(category.get());
        }
    }
