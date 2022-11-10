    package ru.practicum.explorewithme.publicrequest.service;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
    import ru.practicum.explorewithme.mapper.CompilationMapper;
    import ru.practicum.explorewithme.model.Compilation;
    import ru.practicum.explorewithme.repository.CompilationRepository;
    import ru.practicum.explorewithme.repository.FromSizeRequest;
    import java.util.List;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class PublicCompilationServiceImpl implements PublicCompilationService {

        final private CompilationRepository compilationRepository;

        @Override
        public List<CompilationDto> getCompilation(boolean pinned, Integer from, Integer size)
                throws  ObjectNotFoundException {
            final Pageable pageable = FromSizeRequest.of(from, size);
            List<Compilation> listCompilation;
            if (pinned) {
                listCompilation = compilationRepository
                        .findByPinned(pinned, pageable).getContent();
            } else {
                listCompilation = compilationRepository.findAll(pageable).getContent();
            }
            return CompilationMapper.toListCompilationDto(listCompilation);
        }

        @Override
        public CompilationDto getCompilationById(Long compId) throws ObjectNotFoundException {
                Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                        () -> new ObjectNotFoundException("Объект не найден. ",
                                String.format("Compilation with id={} was not found.", compId)));
                return CompilationMapper.toCompilationDto(compilation);
        }
    }
