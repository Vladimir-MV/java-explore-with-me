    package ru.practicum.explorewithme.publicrequest.service;

    import ru.practicum.explorewithme.dto.CategoryDto;

    import java.util.List;

    public interface PublicCategoryService {


        List<CategoryDto> getCategories(Integer from, Integer size);

        CategoryDto getCategoryById(Long catId);
    }
