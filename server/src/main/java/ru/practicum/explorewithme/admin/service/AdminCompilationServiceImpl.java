package ru.practicum.explorewithme.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;
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
    public CompilationDto createCompilation (NewCompilationDto newCompilationDto) throws ConditionsOperationNotMetException {
        //if (!newCompilationDto.isPresent()) throw new ConditionsOperationNotMetException();
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
    public void deleteCompilationById(Long compId) throws RequestErrorException, ObjectNotFoundException {
//        if (!compId.isPresent()) throw new RequestErrorException();
        compilationRepository.deleteById(compId);
//        Optional<Compilation> compilation = compilationRepository.findCompilationById(compId.get());
//        if (!compilation.isPresent()) throw new ObjectNotFoundException(String.format("Compilation with id={} was not found."));
        log.info("Удалена подборка compilationId={}", compId);
    }

    @Override
    public void deleteEventByIdFromCompilation(Long compId, Long eventId) throws RequestErrorException, ObjectNotFoundException {
//        if (!compId.isPresent() && !eventId.isPresent())
//            throw new RequestErrorException();
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId)));
//        if (!compilation.isPresent())
//            throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId.get()));

//        Optional<Event> event = eventRepository.findById(eventId);
//        if (!event.isPresent())
//            throw new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId.get()));
         compilation.getEvents().removeIf(e -> (e.getId().equals(eventId)));
//
//        Compilation compilation = compilationOptional.get();
//        compilation.getEvents().remove(eventId);
        compilationRepository.saveAndFlush(compilation);
        log.info("Удалено событие eventId={} из подборки compilationId={}",eventId, compId);
    }

    @Override
    public void patchEventInCompilationById(Long compId, Long eventId) throws RequestErrorException, ObjectNotFoundException {
//        if (!compId.isPresent() && !eventId.isPresent())
//            throw new RequestErrorException();
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId)));
//        if (!compilation.isPresent())
//            throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId));
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId)));
//        if (!event.isPresent())
//            throw new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId));
        //Compilation compilation = compilationOptional.get();
        compilation.getEvents().add(event);
        compilationRepository.saveAndFlush(compilation);
        log.info("Добавлено событие eventId={} в подборку compilationId={}",eventId, compId);
    }

    @Override
    public void unpinCompilationById(Long compId) throws ObjectNotFoundException {
        Compilation compilation = compilationRepository.findCompilationById(compId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId)));
//        if (!compilation.isPresent())
//            throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId.get()));
        compilation.setPinned(false);
        compilationRepository.saveAndFlush(compilation);
        log.info("Откреплена подборка compilationId={} с главной страницы.", compId);
    }

    @Override
    public void pinCompilationById(Long compId) throws ObjectNotFoundException {
        Compilation compilation = compilationRepository.findCompilationById(compId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId)));
//        if (!compilation.isPresent())
//            throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId.get()));
        compilation.setPinned(true);
        compilationRepository.saveAndFlush(compilation);
        log.info("Закреплена подборка compilationId={} на главную страницу.", compId);
    }
}
