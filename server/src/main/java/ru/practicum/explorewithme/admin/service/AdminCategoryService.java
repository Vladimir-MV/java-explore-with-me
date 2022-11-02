    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;

    public interface AdminCategoryService {

        CategoryDto patchCategoryByIdAndName(CategoryDto categoryDto) throws RequestErrorException;

        CategoryDto createCategory(NewCategoryDto newCategoryDto) throws RequestErrorException;

        CategoryDto deleteCategoryById(Long catId) throws RequestErrorException;
    }
