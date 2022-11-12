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
    public class LocationDto {


        @NotNull
        private float lat;
        @NotNull
        private float lon;
    }
