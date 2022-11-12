    package ru.practicum.explorewithme.admin.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;
    import ru.practicum.explorewithme.mapper.CategoryMapper;
    import ru.practicum.explorewithme.model.Category;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.EventRepository;

    import java.util.Optional;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class AdminCategoryServiceImpl implements AdminCategoryService {
        private final CategoryRepository categoryRepository;
        private final EventRepository eventRepository;

        @Transactional
        @Override
        public CategoryDto patchCategoryByIdAndName(
                CategoryDto categoryDto) {
            Category category = categoryRepository
                    .findById(categoryDto.getId())
                    .orElseThrow(
                        () -> new RequestErrorException("Запрос составлен с ошибкой", "categoryRepository"));
            category.setName(categoryDto.getName());
            categoryRepository.saveAndFlush(category);
            log.info("Изменение категории на категорию categoryDto={}", category.getName());
            return CategoryMapper.toCategoryDto(category);
        }

        @Transactional
        @Override
        public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
            Category category = CategoryMapper.toCategory(newCategoryDto);
            categoryRepository.save(category);
            log.info("Добавление новой категории category={}", category.getName());
            return CategoryMapper.toCategoryDto(category);
        }

        @Transactional
        @Override
        public CategoryDto deleteCategoryById(Long catId) {
            Optional<Category> category = categoryRepository.findById(catId);
            if (category.isEmpty()) {
                new RequestErrorException("Запрос составлен с ошибкой", "такой категории нет.");
            }
            if (eventRepository.findCategoryByIdInEvent(catId).isPresent()) {
                new RequestErrorException("Запрос составлен с ошибкой", " у категории есть events.");
            }
            categoryRepository.deleteById(category.get().getId());
            log.info("Удалена категория category={}", category.get().getName());
            return CategoryMapper.toCategoryDto(category.get());
        }
    }
