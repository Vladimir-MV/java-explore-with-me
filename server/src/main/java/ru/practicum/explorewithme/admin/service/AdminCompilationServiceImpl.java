    package ru.practicum.explorewithme.admin.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.dto.NewCompilationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.CompilationMapper;
    import ru.practicum.explorewithme.model.Compilation;
    import ru.practicum.explorewithme.model.Event;
    import ru.practicum.explorewithme.repository.CompilationRepository;
    import ru.practicum.explorewithme.repository.EventRepository;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class AdminCompilationServiceImpl implements AdminCompilationService{

        private final EventRepository eventRepository;
        private final CompilationRepository compilationRepository;

        @Transactional
        @Override
        public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
            Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
            if (!newCompilationDto.getEvents().isEmpty()) {
                for (Long id : newCompilationDto.getEvents()) {
                    compilation.getEvents().add(eventRepository.findById(id).get());
                }
            }
                compilationRepository.save(compilation);
                log.info("Добавлена новая подборка id={}", compilation.getId());
                return CompilationMapper.toCompilationDto(compilation);
        }

        @Transactional
        @Override
        public void deleteCompilationById(Long compId) {
            compilationRepository.deleteById(compId);
            log.info("Удалена подборка compilationId={}", compId);
        }

        @Transactional
        @Override
        public void deleteEventByIdFromCompilation(Long compId, Long eventId)
                throws ObjectNotFoundException {
            Compilation compilation = compilationRepository
                    .findById(compId).orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("Compilation with id={} was not found.", compId)));
             compilation.getEvents().removeIf(e -> (e.getId().equals(eventId)));
            compilationRepository.saveAndFlush(compilation);
            log.info("Удалено событие eventId={} из подборки compilationId={}",eventId, compId);
        }

        @Transactional
        @Override
        public void patchEventInCompilationById(Long compId, Long eventId)
                throws ObjectNotFoundException {
            Compilation compilation = compilationRepository
                    .findById(compId).orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("Compilation with id={} was not found.", compId)));
            Event event = eventRepository.findById(eventId).orElseThrow(() ->
                            new ObjectNotFoundException("Объект не найден. ",
                            String.format("Event with id={} was not found.", eventId)));
            compilation.getEvents().add(event);
            compilationRepository.saveAndFlush(compilation);
            log.info("Добавлено событие eventId={} в подборку compilationId={}",eventId, compId);
        }

        @Transactional
        @Override
        public void unpinCompilationById(Long compId) throws ObjectNotFoundException {
            Compilation compilation = compilationRepository.findById(compId)
                    .orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("Compilation with id={} was not found.", compId)));
            compilation.setPinned(false);
            compilationRepository.save(compilation);
            log.info("Откреплена подборка compilationId={} с главной страницы.", compId);
        }

        @Transactional
        @Override
        public void pinCompilationById(Long compId) throws ObjectNotFoundException {
            Compilation compilation = compilationRepository.findById(compId)
                    .orElseThrow(() -> new ObjectNotFoundException("Объект не найден. ",
                            String.format("Compilation with id={} was not found.", compId)));
            compilation.setPinned(true);
            compilationRepository.save(compilation);
            log.info("Закреплена подборка compilationId={} на главную страницу.", compId);
        }
    }
