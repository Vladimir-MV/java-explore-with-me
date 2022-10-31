package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;
import ru.practicum.explorewithme.model.Compilation;
import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                EventMapper.toListEventShortDto(compilation.getEvents()),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }
    public static Compilation toCompilation (NewCompilationDto compilationNew) {
        Compilation compilation = new Compilation();
        compilation.setTitle(compilationNew.getTitle());
        compilation.setPinned(compilationNew.getPinned());
        return compilation;
    }

    public static List<CompilationDto> toListCompilationDto(List<Compilation> list) {
        List<CompilationDto> listDto = new ArrayList<>();
        for (Compilation compilation : list) {
            listDto.add(toCompilationDto(compilation));
        }
        return listDto;
    }
}
