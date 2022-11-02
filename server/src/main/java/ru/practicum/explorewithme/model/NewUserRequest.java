    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.validation.constraints.Email;
    import javax.validation.constraints.NotNull;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewUserRequest {
        @NotNull
        @Email
        private String email;
        @NotNull
        private String name;
    }
