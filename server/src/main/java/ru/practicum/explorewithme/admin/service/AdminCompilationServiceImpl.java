package ru.practicum.explorewithme.admin.service;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;

import java.util.Optional;

public class AdminCompilationServiceImpl implements AdminCompilationService{
    @Override
    public CompilationDto createCompilation(Optional<NewCompilationDto> newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilationById(Optional<Long> compId) {

    }

    @Override
    public void deleteEventByIdFromCompilation(Optional<Long> compId, Optional<Long> eventId) {

    }

    @Override
    public void patchEventInCompilationById(Optional<Long> compId, Optional<Long> eventId) {

    }

    @Override
    public void unpinCompilationById(Optional<Long> compId) {

    }

    @Override
    public void pinCompilationById(Optional<Long> compId) {

    }
}
