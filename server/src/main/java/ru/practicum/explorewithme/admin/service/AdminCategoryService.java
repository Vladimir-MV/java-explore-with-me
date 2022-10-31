    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;

    import java.util.Optional;

    public interface AdminCategoryService {

        CategoryDto patchCategoryByIdAndName(Optional<CategoryDto> categoryDto) throws RequestErrorException;

        CategoryDto createCategory(Optional<NewCategoryDto> newCategoryDto) throws RequestErrorException;

        CategoryDto deleteCategoryById(Optional<Long> catId) throws RequestErrorException;
    }
