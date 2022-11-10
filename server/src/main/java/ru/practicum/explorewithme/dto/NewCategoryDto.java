    package ru.practicum.explorewithme.dto;

    import lombok.*;

    import javax.validation.constraints.NotBlank;

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewCategoryDto {
        @NotBlank
        private String name;
    }
