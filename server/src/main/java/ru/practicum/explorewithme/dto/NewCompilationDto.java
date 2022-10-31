    package ru.practicum.explorewithme.dto;

    import lombok.*;

    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.NotNull;
    import java.util.List;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewCompilationDto {
        private List<Long> events;
        @Builder.Default
        private Boolean pinned = false;
        @NotNull
        private String title;

    }
