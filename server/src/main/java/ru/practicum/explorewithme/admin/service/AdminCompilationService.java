package ru.practicum.explorewithme.admin.service;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;

import java.util.Optional;

public interface AdminCompilationService {
    CompilationDto createCompilation(Optional<NewCompilationDto> newCompilationDto) throws ConditionsOperationNotMetException;

    void deleteCompilationById(Optional<Long> compId) throws RequestErrorException, ObjectNotFoundException;

    void deleteEventByIdFromCompilation(Optional<Long> compId, Optional<Long> eventId) throws RequestErrorException, ObjectNotFoundException;

    void patchEventInCompilationById(Optional<Long> compId, Optional<Long> eventId) throws RequestErrorException, ObjectNotFoundException;

    void unpinCompilationById(Optional<Long> compId) throws RequestErrorException, ObjectNotFoundException;

    void pinCompilationById(Optional<Long> compId) throws RequestErrorException, ObjectNotFoundException;
}
