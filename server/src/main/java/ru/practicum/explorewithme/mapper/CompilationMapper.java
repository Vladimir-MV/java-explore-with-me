package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.model.Compilation;
import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                EventMapper.toListEventShortDto(compilation.getEvent()),
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }

    public static List<CompilationDto> toListCompilationDto(List<Compilation> list) {
        List<CompilationDto> listDto = new ArrayList<>();
        for (Compilation compilation : list) {
            listDto.add(toCompilationDto(compilation));
        }
        return listDto;
    }
}
