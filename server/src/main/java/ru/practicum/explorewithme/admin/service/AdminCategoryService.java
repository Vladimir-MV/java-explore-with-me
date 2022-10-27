    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.EventFullDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.dto.ParticipationRequestDto;
    import ru.practicum.explorewithme.model.AdminUpdateEventRequest;

    import java.util.List;
    import java.util.Optional;

    public interface AdminCategoryService {

        CategoryDto patchCategoryByIdAndName(Optional<CategoryDto> categoryDto);

        NewCategoryDto createCategory(Optional<NewCategoryDto> newCategoryDto);

        void deleteCategoryById(Optional<Long> catId);
    }
