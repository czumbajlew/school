package pl.kcit.school.student;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.testcontainers.shaded.org.hamcrest.Matchers;
import pl.kcit.school.internal.Gender;
import pl.kcit.school.internal.exception.ErrorType;
import pl.kcit.school.internal.exception.InternalBusinessException;
import pl.kcit.school.suite.AppUnitTest;

import javax.swing.text.html.Option;
import java.util.Optional;

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
        StudentDto givenStudent = StudentStub.notAddedMaleStudentDto();
        Student addedStudent = StudentStub.addedMaleStudent();
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
        assertThat(student.id()).isEqualTo(1L);
        assertThat(student.firstName()).isEqualTo(StudentStub.MALE_STUDENT_FIRSTNAME);
        assertThat(student.lastName()).isEqualTo(StudentStub.MALE_STUDENT_LASTNAME);
        assertThat(student.dateOfBirth()).isEqualTo(StudentStub.MALE_STUDENT_DATE_OF_BIRTH);
        assertThat(student.gender()).isEqualTo(Gender.MALE);
    }

    @Test
    void shouldFailToAddStudent() {
        StudentDto givenStudent = StudentStub.notAddedFemaleStudentDto();
        ArgumentCaptor<Student> convertedStudent = ArgumentCaptor.forClass(Student.class);

        when(studentRepository.save(convertedStudent.capture()))
                .thenThrow(new RuntimeException("Fail to safe to database"));

        Throwable throwable = catchThrowable(() -> studentService.addStudent(givenStudent));

        assertThat(throwable).isInstanceOf(InternalBusinessException.class);
        InternalBusinessException exception = (InternalBusinessException) throwable;
        assertThat(exception.getType()).isEqualTo(ErrorType.DATABASE);
        assertThat(exception.getMessage()).isEqualTo("Fail to safe to database");
    }

    @Test
    void shouldGetStudent() {
        Optional<Student> givenStudent = Optional.of(StudentStub.addedMaleStudent());
        long id = 1L;

        doReturn(givenStudent)
                .when(studentRepository).findById(id);
        StudentDto student = studentService.getStudent(id);

        assertThat(student).isNotNull();
        assertThat(student.firstName()).isEqualTo(StudentStub.MALE_STUDENT_FIRSTNAME);
        assertThat(student.lastName()).isEqualTo(StudentStub.MALE_STUDENT_LASTNAME);
        assertThat(student.gender()).isEqualTo(Gender.MALE);
        assertThat(student.dateOfBirth()).isEqualTo(StudentStub.MALE_STUDENT_DATE_OF_BIRTH);
    }

    @Test
    void shouldThrowErrorForNotFoundedStudent() {
        long id = 1L;

        doReturn(Optional.empty())
                .when(studentRepository).findById(id);

        Throwable throwable = catchThrowable(() -> studentService.getStudent(id));

        assertThat(throwable).isInstanceOf(InternalBusinessException.class);
        InternalBusinessException exception = (InternalBusinessException) throwable;
        assertThat(exception.getType()).isEqualTo(ErrorType.NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo("Student for given id was not found");
    }

    @Test
    void shouldDeleteStudent() {
        Optional<Student> student = Optional.of(StudentStub.addedFemaleStudent());
        long id = 1L;

        doReturn(student)
                .when(studentRepository).findById(id);
        studentService.deleteStudent(id);

        Mockito.verify(studentRepository, times(1)).delete(Mockito.any(Student.class));
    }

    @Test
    void shouldFailToDeleteStudent() {
        long id = 1L;

        doReturn(Optional.empty())
                .when(studentRepository).findById(id);

        Throwable throwable = catchThrowable(() -> studentService.deleteStudent(id));

        assertThat(throwable).isInstanceOf(InternalBusinessException.class);
        InternalBusinessException exception = (InternalBusinessException) throwable;
        assertThat(exception.getType()).isEqualTo(ErrorType.NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo("Student is not present. Cannot be deleted");
    }

    @Test
    void shouldUpdateStudent() {
        String newLastName = "NewLastName";
        long id = 2L;
        Optional<Student> existingStudent = Optional.of(StudentStub.addedFemaleStudent());
        Student updatedStudent = StudentStub.addedFemaleStudent();
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
        long id = 2L;

        doReturn(Optional.empty())
                .when(studentRepository).findById(id);

        Throwable throwable = catchThrowable(() -> studentService.updateStudent(id, StudentStub.addedMaleStudentDto()));

        assertThat(throwable).isInstanceOf(InternalBusinessException.class);
        InternalBusinessException exception = (InternalBusinessException) throwable;
        assertThat(exception.getType()).isEqualTo(ErrorType.NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo("Student for given id was not found");
    }

}