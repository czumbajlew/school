package pl.kcit.school.student;

import org.springframework.stereotype.Component;
import pl.kcit.school.internal.converter.ObjectConverter;

@Component
class StudentConverter implements ObjectConverter<Student, StudentDto> {

    @Override
    public Student convertTo(StudentDto source) {
        if (source == null) {
            return null;
        }

        return new Student(
                source.firstName(),
                source.lastName(),
                source.dateOfBirth(),
                source.gender()
        );
    }

    @Override
    public StudentDto convertFrom(Student source) {
        if (source == null) {
            return null;
        }

        return new StudentDto(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getDateOfBirth(),
                source.getGender()
        );
    }

}
