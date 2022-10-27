package ru.practicum.explorewithme.admin.service;

import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.dto.NewCategoryDto;

import java.util.Optional;

public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Override
    public CategoryDto patchCategoryByIdAndName(Optional<CategoryDto> categoryDto) {
        return null;
    }

    @Override
    public NewCategoryDto createCategory(Optional<NewCategoryDto> newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategoryById(Optional<Long> catId) {

    }
}
