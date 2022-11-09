    package ru.practicum.explorewithme.publicrequest.service;

    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;

    import java.util.List;

    public interface PublicCompilationService {
        List<CompilationDto> getCompilation(boolean pinned, Integer from, Integer size)
                throws ObjectNotFoundException;

        CompilationDto getCompilationById(Long compId) throws ObjectNotFoundException;
    }
