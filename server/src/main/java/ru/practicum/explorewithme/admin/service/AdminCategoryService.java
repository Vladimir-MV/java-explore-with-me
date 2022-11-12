    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;

    public interface AdminCategoryService {

        CategoryDto patchCategoryByIdAndName(CategoryDto categoryDto);

        CategoryDto createCategory(NewCategoryDto newCategoryDto);

        CategoryDto deleteCategoryById(Long catId);
    }
