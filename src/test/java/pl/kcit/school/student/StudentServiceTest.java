package pl.kcit.school.student;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.kcit.school.internal.Gender;
import pl.kcit.school.internal.exception.DatabaseException;
import pl.kcit.school.internal.exception.NotFoundException;
import pl.kcit.school.suite.AppUnitTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@AppUnitTest
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Spy
    private StudentConverter studentConverter;

    @InjectMocks
    private StudentService studentService;

    @Test
    void shouldAddStudent() {
        UUID id = UUID.randomUUID();
        StudentDto givenStudent = StudentStub.notAddedMaleStudentDto();
        Student addedStudent = StudentStub.addedMaleStudent(id);
        ArgumentCaptor<Student> convertedStudent = ArgumentCaptor.forClass(Student.class);

        doReturn(addedStudent)
                .when(studentRepository).save(convertedStudent.capture());
        StudentDto student = studentService.addStudent(givenStudent);

        assertThat(convertedStudent.getValue()).isNotNull();
        assertThat(convertedStudent.getValue().getId()).isNull();
        assertThat(convertedStudent.getValue().getFirstName()).isEqualTo(StudentStub.MALE_STUDENT_FIRSTNAME);
        assertThat(convertedStudent.getValue().getLastName()).isEqualTo(StudentStub.MALE_STUDENT_LASTNAME);
        assertThat(convertedStudent.getValue().getDateOfBirth()).isEqualTo(StudentStub.MALE_STUDENT_DATE_OF_BIRTH);
        assertThat(convertedStudent.getValue().getGender()).isEqualTo(Gender.MALE);

        assertThat(student).isNotNull();
        assertThat(student.id()).isEqualTo(id);
        assertThat(student.firstName()).isEqualTo(StudentStub.MALE_STUDENT_FIRSTNAME);
        assertThat(student.lastName()).isEqualTo(StudentStub.MALE_STUDENT_LASTNAME);
        assertThat(student.dateOfBirth()).isEqualTo(StudentStub.MALE_STUDENT_DATE_OF_BIRTH.toString());
        assertThat(student.gender()).isEqualTo(Gender.MALE);
    }

    @Test
    void shouldFailToAddStudent() {
        StudentDto givenStudent = StudentStub.notAddedFemaleStudentDto();
        ArgumentCaptor<Student> convertedStudent = ArgumentCaptor.forClass(Student.class);

        when(studentRepository.save(convertedStudent.capture()))
                .thenThrow(new RuntimeException("Fail to save to database"));

        Throwable throwable = catchThrowable(() -> studentService.addStudent(givenStudent));

        assertThat(throwable).isInstanceOf(DatabaseException.class);
        DatabaseException exception = (DatabaseException) throwable;
        assertThat(exception.getMessage()).isEqualTo("Fail to save to database");
    }

    @Test
    void shouldGetStudent() {
        UUID id = UUID.randomUUID();
        Optional<Student> givenStudent = Optional.of(StudentStub.addedMaleStudent(id));

        doReturn(givenStudent)
                .when(studentRepository).findById(id);
        StudentDto student = studentService.getStudent(id);

        assertThat(student).isNotNull();
        assertThat(student.firstName()).isEqualTo(StudentStub.MALE_STUDENT_FIRSTNAME);
        assertThat(student.lastName()).isEqualTo(StudentStub.MALE_STUDENT_LASTNAME);
        assertThat(student.gender()).isEqualTo(Gender.MALE);
        assertThat(student.dateOfBirth()).isEqualTo(StudentStub.MALE_STUDENT_DATE_OF_BIRTH.toString());
    }

    @Test
    void shouldThrowErrorForNotFoundedStudent() {
        UUID id = UUID.randomUUID();

        doReturn(Optional.empty())
                .when(studentRepository).findById(id);

        Throwable throwable = catchThrowable(() -> studentService.getStudent(id));

        assertThat(throwable).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) throwable;
        assertThat(exception.getMessage()).isEqualTo("Student for given id was not found");
    }

    @Test
    void shouldDeleteStudent() {
        UUID id = UUID.randomUUID();
        Optional<Student> student = Optional.of(StudentStub.addedFemaleStudent(id));

        doReturn(student)
                .when(studentRepository).findById(id);
        studentService.deleteStudent(id);

        Mockito.verify(studentRepository, times(1)).delete(Mockito.any(Student.class));
    }

    @Test
    void shouldFailToDeleteStudent() {
        UUID id = UUID.randomUUID();

        doReturn(Optional.empty())
                .when(studentRepository).findById(id);

        Throwable throwable = catchThrowable(() -> studentService.deleteStudent(id));

        assertThat(throwable).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) throwable;
        assertThat(exception.getMessage()).isEqualTo("Student is not present. Cannot be deleted");
    }

    @Test
    void shouldUpdateStudent() {
        String newLastName = "NewLastName";
        UUID id = UUID.randomUUID();
        Optional<Student> existingStudent = Optional.of(StudentStub.addedFemaleStudent(id));
        Student updatedStudent = StudentStub.addedFemaleStudent(id);
        updatedStudent.setLastName(newLastName);
        StudentDto requestStudent = new StudentDto(
                id,
                StudentStub.FEMALE_STUDENT_FIRSTNAME,
                newLastName,
                StudentStub.FEMALE_STUDENT_DATE_OF_BIRTH,
                Gender.FEMALE
        );

        doReturn(existingStudent)
                .when(studentRepository).findById(id);
        doReturn(updatedStudent)
                .when(studentRepository).save(ArgumentMatchers.argThat(el -> el.getId().equals(id)));

        StudentDto studentDto = studentService.updateStudent(id, requestStudent);

        assertThat(studentDto).isNotNull();
        assertThat(studentDto.firstName()).isEqualTo(StudentStub.FEMALE_STUDENT_FIRSTNAME);
        assertThat(studentDto.lastName()).isEqualTo(newLastName);
    }

    @Test
    void shouldFailToUpdateNotFoundedStudent() {
        UUID id = UUID.randomUUID();

        doReturn(Optional.empty())
                .when(studentRepository).findById(id);

        Throwable throwable = catchThrowable(() -> studentService.updateStudent(id, StudentStub.addedMaleStudentDto(id)));

        assertThat(throwable).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) throwable;
        assertThat(exception.getMessage()).isEqualTo("Student for given id was not found");
    }

}