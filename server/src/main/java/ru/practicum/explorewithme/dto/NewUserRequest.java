    package ru.practicum.explorewithme.dto;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.validation.constraints.Email;
    import javax.validation.constraints.NotBlank;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class NewUserRequest {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String name;
    }
