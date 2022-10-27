package ru.practicum.explorewithme.admin.service;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;

import java.util.Optional;

public interface AdminCompilationService {
    CompilationDto createCompilation(Optional<NewCompilationDto> newCompilationDto);

    void deleteCompilationById(Optional<Long> compId);

    void deleteEventByIdFromCompilation(Optional<Long> compId, Optional<Long> eventId);

    void patchEventInCompilationById(Optional<Long> compId, Optional<Long> eventId);

    void unpinCompilationById(Optional<Long> compId);

    void pinCompilationById(Optional<Long> compId);
}
