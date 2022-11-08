    package ru.practicum.explorewithme.dto;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.validation.constraints.NotNull;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewLocationGroupDto {
        private Long id;
        @NotNull
        private String name;
        @NotNull
        private float lat;
        @NotNull
        private float lon;
        @NotNull
        private float radius;
        private String description;
    }
