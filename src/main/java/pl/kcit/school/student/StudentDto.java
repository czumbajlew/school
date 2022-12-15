package pl.kcit.school.student;

import pl.kcit.school.internal.Gender;

import java.time.LocalDate;

record StudentDto(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Gender gender
) {

}
