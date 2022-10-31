package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.List;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class CompilationDto {
        @NotNull
        private Long id;
        private List<EventShortDto> events;
        @NotNull
        private Boolean pinned;
        @NotNull
        private String title;

    }
