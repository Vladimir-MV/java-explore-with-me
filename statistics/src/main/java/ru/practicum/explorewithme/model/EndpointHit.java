    package ru.practicum.explorewithme.model;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import javax.persistence.*;
    import javax.validation.constraints.Pattern;
    import java.time.LocalDateTime;

    @Entity
    @Table(name = "stats")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class EndpointHit {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String app;
        private String uri;
       // @Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
        private String ip;
        private LocalDateTime timestamp;
        private Long hits;

    }
