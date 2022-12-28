package pl.kcit.school.student;

import org.springframework.stereotype.Service;
import pl.kcit.school.internal.exception.DatabaseException;
import pl.kcit.school.internal.exception.NotFoundException;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentConverter studentConverter;

    private final Supplier<NotFoundException> studentNotFoundException = () -> new NotFoundException("Student for given id was not found");

    public StudentService(
            StudentRepository studentRepository,
            StudentConverter studentConverter
    ) {
        this.studentRepository = studentRepository;
        this.studentConverter = studentConverter;
    }

    public StudentDto getStudent(UUID id) {
        return studentRepository.findById(id)
                .map(studentConverter::convertFrom)
                .orElseThrow(studentNotFoundException);
    }

    public StudentDto addStudent(StudentDto studentDto) {
        try {
            Student addedStudent = studentRepository.save(studentConverter.convertTo(studentDto));
            return studentConverter.convertFrom(addedStudent);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public StudentDto updateStudent(UUID id, StudentDto studentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(studentNotFoundException);

        try {
            Student savedStudent = studentRepository.save(updatedStudent(student, studentDto));

            return studentConverter.convertFrom(savedStudent);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void deleteStudent(UUID id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isEmpty()) {
            throw new NotFoundException("Student is not present. Cannot be deleted");
        }

        try {
            studentRepository.delete(optionalStudent.get());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Student updatedStudent(Student existingStudent, StudentDto updatedStudent) {
        existingStudent.setFirstName(mergeField(existingStudent.getFirstName(), updatedStudent.firstName()));
        existingStudent.setLastName(mergeField(existingStudent.getLastName(), updatedStudent.lastName()));
        existingStudent.setGender(mergeField(existingStudent.getGender(), updatedStudent.gender()));
        existingStudent.setDateOfBirth(mergeField(existingStudent.getDateOfBirth(), updatedStudent.dateOfBirth()));

        return existingStudent;
    }

    private <T> T mergeField(T existed, T updated) {
        if (updated == null) {
            return existed;
        }

        return updated;
    }

}
