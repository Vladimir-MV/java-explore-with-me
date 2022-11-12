    package ru.practicum.explorewithme.publicrequest.service;

    import ru.practicum.explorewithme.dto.CompilationDto;

    import java.util.List;

    public interface PublicCompilationService {

        List<CompilationDto> getCompilation(boolean pinned, Integer from, Integer size);


        CompilationDto getCompilationById(Long compId);
    }
