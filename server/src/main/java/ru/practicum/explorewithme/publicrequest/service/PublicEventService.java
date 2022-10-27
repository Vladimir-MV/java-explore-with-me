package ru.practicum.explorewithme.publicrequest.service;

import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.exceptions.MethodExceptions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface PublicEventService {
    EventShortDto[] getEventsByTextAndCategory(String text, CategoryDto[] categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    List<EventFullDto> getEventById(Optional<Long> id, HttpServletRequest request) throws MethodExceptions;
}
