    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.exceptions.MethodExceptions;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.CompilationMapper;
    import ru.practicum.explorewithme.model.Compilation;
    import ru.practicum.explorewithme.repository.CompilationRepository;
    import ru.practicum.explorewithme.repository.EventRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;

    import java.util.List;
    import java.util.Optional;

    @Slf4j
    @Service
    public class PublicCompilationServiceImpl implements PublicCompilationService{
        private CompilationRepository compilationRepository;
        private EventRepository eventRepository;
        @Autowired
        public PublicCompilationServiceImpl (EventRepository eventRepository, CompilationRepository compilationRepository) {
            this.compilationRepository = compilationRepository;
            this.eventRepository = eventRepository;
        }
        @Override
        public List<CompilationDto> getCompilation(Boolean pinned, Integer from, Integer size) throws  ObjectNotFoundException {
            final Pageable pageable = FromSizeRequest.of(from, size);
            List<Compilation> listCompilation;
            if (pinned != null) {
                listCompilation = compilationRepository.findCompilationByPinned(pinned, pageable).getContent();
            } else {
                listCompilation = compilationRepository.findCompilation(pageable).getContent();
            }
            if (!listCompilation.isEmpty()) return CompilationMapper.toListCompilationDto(listCompilation);

            throw new ObjectNotFoundException(String.format("Compilation with pinned={} was not found.", pinned));

        }
        @Override
        public CompilationDto getCompilationById(Optional<Long> compId) throws ObjectNotFoundException {
            if (compId.isPresent()) {
                Optional<Compilation> compilation = compilationRepository.findCompilationById(compId.get());
                if (compilation.isPresent()) {
                    return CompilationMapper.toCompilationDto(compilation.get());
                } else {
                    throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId));
                }
            } else {
                throw new ObjectNotFoundException(String.format("Only pending or canceled events can be changed"));
            }
        }
    }
