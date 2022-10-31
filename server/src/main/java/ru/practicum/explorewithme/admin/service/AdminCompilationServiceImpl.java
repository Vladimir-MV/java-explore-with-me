package ru.practicum.explorewithme.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;
import ru.practicum.explorewithme.exceptions.ConditionsOperationNotMetException;
import ru.practicum.explorewithme.exceptions.MethodExceptions;
import ru.practicum.explorewithme.exceptions.ObjectNotFoundException;
import ru.practicum.explorewithme.exceptions.RequestErrorException;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.mapper.UserMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.UserRepository;

import java.util.List;
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
    public CompilationDto createCompilation (Optional<NewCompilationDto> newCompilationDto) throws ConditionsOperationNotMetException {
        if (!newCompilationDto.isPresent()) throw new ConditionsOperationNotMetException();
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto.get());
        //if (!newCompilationDto.get().getEvents().isEmpty()) {
           // Optional<Event> event;
            for (Long id: newCompilationDto.get().getEvents()){
                 compilation.getEvents().add(eventRepository.findEventById(id).get());
            }
        //}
        compilationRepository.save(compilation);
        log.info("Добавлена новая подборка id={}", compilation.getId());
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void deleteCompilationById(Optional<Long> compId) throws RequestErrorException, ObjectNotFoundException {
        if (!compId.isPresent()) throw new RequestErrorException();
        Optional<Compilation> compilation = compilationRepository.findCompilationById(compId.get());
        if (!compilation.isPresent()) throw new ObjectNotFoundException(String.format("Compilation with id={} was not found."));
        compilationRepository.delete(compilation.get());
        log.info("Удалена подборка compilationId={}", compilation.get().getId());
    }

    @Override
    public void deleteEventByIdFromCompilation(Optional<Long> compId, Optional<Long> eventId) throws RequestErrorException, ObjectNotFoundException {
        if (!compId.isPresent() && !eventId.isPresent())
            throw new RequestErrorException();
        Optional<Compilation> compilationOptional = compilationRepository.findCompilationById(compId.get());
        if (!compilationOptional.isPresent())
            throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId.get()));
            Event event = eventRepository.findEventById(eventId.get()).get();
        if (!compilationOptional.get().getEvents().contains(event))
            throw new ObjectNotFoundException(String.format("Event with id={} in compilation was not found.", eventId.get()));
        Compilation compilation = compilationOptional.get();
        compilation.getEvents().remove(eventId);
        compilationRepository.save(compilation);
        log.info("Удалено событие eventId={} из подборки compilationId={}",eventId.get(), compId.get());
    }

    @Override
    public void patchEventInCompilationById(Optional<Long> compId, Optional<Long> eventId) throws RequestErrorException, ObjectNotFoundException {
        if (!compId.isPresent() && !eventId.isPresent())
            throw new RequestErrorException();
        Optional<Compilation> compilationOptional = compilationRepository.findCompilationById(compId.get());
        if (!compilationOptional.isPresent())
            throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId.get()));
        Optional<Event> event = eventRepository.findEventById(eventId.get());
        if (!event.isPresent())
            throw new ObjectNotFoundException(String.format("Event with id={} was not found.", eventId.get()));
        Compilation compilation = compilationOptional.get();
        compilation.getEvents().add(event.get());
        compilationRepository.save(compilation);
        log.info("Добавлено событие eventId={} в подборку compilationId={}",eventId.get(), compId.get());
    }

    @Override
    public void unpinCompilationById(Optional<Long> compId) throws RequestErrorException, ObjectNotFoundException {
        if (!compId.isPresent())
            throw new RequestErrorException();
        Optional<Compilation> compilationOptional = compilationRepository.findCompilationById(compId.get());
        if (!compilationOptional.isPresent())
            throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId.get()));
        Compilation compilation = compilationOptional.get();
        compilation.setPinned(false);
        compilationRepository.save(compilation);
        log.info("Откреплена подборка compilationId={} с главной страницы.", compId.get());
    }

    @Override
    public void pinCompilationById(Optional<Long> compId) throws RequestErrorException, ObjectNotFoundException {
        if (!compId.isPresent())
            throw new RequestErrorException();
        Optional<Compilation> compilationOptional = compilationRepository.findCompilationById(compId.get());
        if (!compilationOptional.isPresent())
            throw new ObjectNotFoundException(String.format("Compilation with id={} was not found.", compId.get()));
        Compilation compilation = compilationOptional.get();
        compilation.setPinned(true);
        compilationRepository.save(compilation);
        log.info("Закреплена подборка compilationId={} на главную страницу.", compId.get());
    }
}
