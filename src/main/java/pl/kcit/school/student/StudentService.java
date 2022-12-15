package pl.kcit.school.student;

import org.springframework.stereotype.Service;
import pl.kcit.school.internal.exception.ErrorType;
import pl.kcit.school.internal.exception.InternalBusinessException;

import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentConverter studentConverter;

    public StudentService(
            StudentRepository studentRepository,
            StudentConverter studentConverter
    ) {
        this.studentRepository = studentRepository;
        this.studentConverter = studentConverter;
    }

    public StudentDto getStudent(long id) {
        return studentRepository.findById(id)
                .map(studentConverter::convertFrom)
                .orElseThrow(() -> InternalBusinessException
                        .builder()
                        .type(ErrorType.NOT_FOUND)
                        .message("Student for given id was not found")
                        .build());
    }

    public StudentDto addStudent(StudentDto studentDto) {
        try {
            Student addedStudent = studentRepository.save(studentConverter.convertTo(studentDto));
            return studentConverter.convertFrom(addedStudent);
        } catch (Exception e) {
            throw InternalBusinessException
                    .builder()
                    .type(ErrorType.DATABASE)
                    .message(e.getMessage())
                    .build();
        }
    }

    public StudentDto updateStudent(long id, StudentDto studentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> InternalBusinessException
                        .builder()
                        .type(ErrorType.NOT_FOUND)
                        .message("Student for given id was not found")
                        .build()
                );

        try {
            Student savedStudent = studentRepository.save(updatedStudent(student, studentDto));

            return studentConverter.convertFrom(savedStudent);
        } catch (Exception e) {
            throw InternalBusinessException
                    .builder()
                    .type(ErrorType.DATABASE)
                    .message(e.getMessage())
                    .build();
        }
    }

    public void deleteStudent(long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isEmpty()) {
            throw InternalBusinessException
                    .builder()
                    .type(ErrorType.NOT_FOUND)
                    .message("Student is not present. Cannot be deleted")
                    .build();
        }

        try {
            studentRepository.delete(optionalStudent.get());
        } catch (Exception e) {
            throw InternalBusinessException
                    .builder()
                    .type(ErrorType.DATABASE)
                    .message(e.getMessage())
                    .build();
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
