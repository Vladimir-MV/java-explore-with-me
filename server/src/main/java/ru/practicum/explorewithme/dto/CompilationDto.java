package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.List;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class CompilationDto {
        private List<EventShortDto> event;
        private Long id;
        private Boolean pinned;
        private String title;
    }
