    package ru.practicum.explorewithme.admin.service;

    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.dto.NewCompilationDto;
    import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.exceptions.RequestErrorException;

    public interface AdminCompilationService {
        CompilationDto createCompilation(NewCompilationDto newCompilationDto)
                throws ConditionsOperationNotMetException;
        void deleteCompilationById(Long compId)
                throws RequestErrorException, ObjectNotFoundException;
        void deleteEventByIdFromCompilation(Long compId, Long eventId)
                throws RequestErrorException, ObjectNotFoundException;
        void patchEventInCompilationById(Long compId, Long eventId)
                throws RequestErrorException, ObjectNotFoundException;
        void unpinCompilationById(Long compId)
                throws RequestErrorException, ObjectNotFoundException;
        void pinCompilationById(Long compId)
                throws RequestErrorException, ObjectNotFoundException;
    }

