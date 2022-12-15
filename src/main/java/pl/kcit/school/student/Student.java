package pl.kcit.school.student;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.kcit.school.internal.Gender;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "st_student")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
class Student implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    Student(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            Gender gender
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }
}
