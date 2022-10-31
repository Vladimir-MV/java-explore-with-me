package ru.practicum.explorewithme.publicrequest.service;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PublicCompilationService {
    List<CompilationDto> getCompilation(Boolean pinned, Integer from, Integer size)
            throws ObjectNotFoundException;

    CompilationDto getCompilationById(Optional<Long> compId) throws ObjectNotFoundException;
}
