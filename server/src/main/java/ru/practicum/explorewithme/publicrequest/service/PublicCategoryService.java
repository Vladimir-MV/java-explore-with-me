package ru.practicum.explorewithme.publicrequest.service;

import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDto> getCategories(Integer from, Integer size) throws ObjectNotFoundException;

    CategoryDto getCategoryById(Long catId)
            throws RequestErrorException, ObjectNotFoundException;
}
