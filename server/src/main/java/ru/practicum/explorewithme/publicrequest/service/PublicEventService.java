package ru.practicum.explorewithme.publicrequest.service;

import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

public interface PublicEventService {

    //  LocalDateTime anotherDateTime = LocalDateTime.parse("22.02.2022, 22:22", formatter);
    List<EventShortDto> getEventsByTextAndCategory(String text, List<Long> categories,
                                                   Boolean rangeStart, String rangeEnd, String paid, Boolean onlyAvailable, String sort,
                                                   Integer from, Integer size, HttpServletRequest request) throws ObjectNotFoundException;

    EventFullDto getEventById(@NotBlank Long id, HttpServletRequest request) throws ObjectNotFoundException, RequestErrorException;

   // EventShortDto[] getEventsByTextAndCategory(String text, CategoryDto[] categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);
}
