package pl.kcit.school.student;

import pl.kcit.school.internal.Gender;

import java.time.LocalDate;
import java.util.UUID;

record StudentDto(
        UUID id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Gender gender
) {

}
