    package ru.practicum.explorewithme.admin.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.CategoryMapper;
    import ru.practicum.explorewithme.model.Category;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.EventRepository;

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
        public CategoryDto patchCategoryByIdAndName(CategoryDto categoryDto) throws RequestErrorException {
            Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(RequestErrorException::new);
            category.setName(categoryDto.getName());
            categoryRepository.saveAndFlush(category);
            log.info("Изменение категории на категорию categoryDto={}", category.getName());
            return CategoryMapper.toCategoryDto(category);
        }

        @Override
        public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
            Category category = CategoryMapper.toCategory(newCategoryDto);
            categoryRepository.saveAndFlush(category);
            log.info("Добавление новой категории category={}", category.getName());
            return CategoryMapper.toCategoryDto(category);
        }

        @Override
        public CategoryDto deleteCategoryById(Long catId) throws RequestErrorException {
            Category category = categoryRepository.findById(catId).orElseThrow(RequestErrorException::new);
            categoryRepository.deleteById(category.getId());
            log.info("Удалена категория category={}", category.getName());
            return CategoryMapper.toCategoryDto(category);
        }
    }
