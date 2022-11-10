    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.persistence.*;

    @Entity
    @Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class User {

        public User(String email, String name) {
            this.email = email;
            this.name = name;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;
        @Column(name="email", nullable = false)
        private String email;
        @Column(name="name", nullable = false)
        private String name;
    }
