    package ru.practicum.explorewithme.dto;

    import lombok.*;

    import javax.validation.constraints.NotBlank;
    import java.util.List;
    import java.util.Set;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewCompilationDto {
        @NotBlank
        private String title;
        private List<Long> events;
        private boolean pinned;


    }
