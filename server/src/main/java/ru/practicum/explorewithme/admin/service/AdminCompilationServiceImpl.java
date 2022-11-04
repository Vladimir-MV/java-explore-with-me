    package ru.practicum.explorewithme.admin.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.dto.NewCompilationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.CompilationMapper;
    import ru.practicum.explorewithme.model.Compilation;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.repository.CategoryRepository;
    import ru.practicum.explorewithme.repository.CompilationRepository;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.UserRepository;

    @Slf4j
    @Service
    public class AdminCompilationServiceImpl implements AdminCompilationService{
        private EventRepository eventRepository;
        private UserRepository userRepository;
        private CategoryRepository categoryRepository;
        private CompilationRepository compilationRepository;

        @Autowired
        public AdminCompilationServiceImpl (EventRepository eventRepository,  UserRepository userRepository,
                                      CategoryRepository categoryRepository, CompilationRepository compilationRepository) {
            this.eventRepository = eventRepository;
            this.userRepository = userRepository;
            this.categoryRepository = categoryRepository;
            this.compilationRepository = compilationRepository;
        }
        @Override
        public CompilationDto createCompilation (NewCompilationDto newCompilationDto) {
            Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
            if (!newCompilationDto.getEvents().isEmpty()) {
                for (Long id : newCompilationDto.getEvents()) {
                    compilation.getEvents().add(eventRepository.findById(id).get());
                }
            }
                compilationRepository.saveAndFlush(compilation);
                log.info("Добавлена новая подборка id={}", compilation.getId());
                return CompilationMapper.toCompilationDto(compilation);
        }

        @Override
        public void deleteCompilationById(Long compId) {
            compilationRepository.deleteById(compId);
            log.info("Удалена подборка compilationId={}", compId);
        }

        @Override
        public void deleteEventByIdFromCompilation(Long compId, Long eventId) throws ObjectNotFoundException {
            Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId)));
             compilation.getEvents().removeIf(e -> (e.getId().equals(eventId)));
            compilationRepository.saveAndFlush(compilation);
            log.info("Удалено событие eventId={} из подборки compilationId={}",eventId, compId);
        }

        @Override
        public void patchEventInCompilationById(Long compId, Long eventId) throws ObjectNotFoundException {
            Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId)));
            Event event = eventRepository.findById(eventId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId)));
            compilation.getEvents().add(event);
            compilationRepository.saveAndFlush(compilation);
            log.info("Добавлено событие eventId={} в подборку compilationId={}",eventId, compId);
        }

        @Override
        public void unpinCompilationById(Long compId) throws ObjectNotFoundException {
            Compilation compilation = compilationRepository.findCompilationById(compId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId)));
            compilation.setPinned(false);
            compilationRepository.saveAndFlush(compilation);
            log.info("Откреплена подборка compilationId={} с главной страницы.", compId);
        }

        @Override
        public void pinCompilationById(Long compId) throws ObjectNotFoundException {
            Compilation compilation = compilationRepository.findCompilationById(compId).orElseThrow(
                    () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId)));
            compilation.setPinned(true);
            compilationRepository.saveAndFlush(compilation);
            log.info("Закреплена подборка compilationId={} на главную страницу.", compId);
        }
    }
