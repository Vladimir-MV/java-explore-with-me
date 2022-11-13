    package ru.practicum.explorewithme.model;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.persistence.*;

    @Entity
    @Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Category {


        public Category(String name) {
            this.name = name;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        private Long id;
        @Column(nullable = false)
        private String name;
    }
