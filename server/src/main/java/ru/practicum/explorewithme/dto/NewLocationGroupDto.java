    package ru.practicum.explorewithme.dto;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import javax.validation.constraints.NotNull;
    import javax.validation.constraints.NotBlank;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewLocationGroupDto {

        @NotBlank
        private String name;
        @NotNull
        private float lat;
        @NotNull
        private float lon;
        @NotNull
        private float radius;
        private String description;

    }
