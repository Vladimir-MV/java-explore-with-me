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
    public class LocationGroupDto {

        @NotNull
        private Long id;
        private String name;
        private float lat;
        private float lon;
        private float radius;
        private String description;
    }
