    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.dto.NewCompilationDto;

    public interface AdminCompilationService {

        CompilationDto createCompilation(NewCompilationDto newCompilationDto);

        void deleteCompilationById(Long compId);

        void deleteEventByIdFromCompilation(Long compId, Long eventId);

        void patchEventInCompilationById(Long compId, Long eventId);

        void unpinCompilationById(Long compId);

        void pinCompilationById(Long compId);
    }

