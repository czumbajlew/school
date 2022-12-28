package pl.kcit.school.student;

import pl.kcit.school.internal.Gender;

import java.time.LocalDate;
import java.util.UUID;

public class StudentStub {

    public static final String MALE_STUDENT_FIRSTNAME = "James";
    public static final String MALE_STUDENT_LASTNAME = "Bond";
    public static final LocalDate MALE_STUDENT_DATE_OF_BIRTH = LocalDate.of(2010, 8, 12);
    public static final String FEMALE_STUDENT_FIRSTNAME = "Kate";
    public static final String FEMALE_STUDENT_LASTNAME = "Cash";
    public static final LocalDate FEMALE_STUDENT_DATE_OF_BIRTH = LocalDate.of(2010, 5, 23);

    public static Student notAddedMaleStudent() {
        return new Student(
                null,
                MALE_STUDENT_FIRSTNAME,
                MALE_STUDENT_LASTNAME,
                MALE_STUDENT_DATE_OF_BIRTH,
                Gender.MALE
        );
    }

    public static StudentDto notAddedMaleStudentDto() {
        return new StudentDto(
                null,
                MALE_STUDENT_FIRSTNAME,
                MALE_STUDENT_LASTNAME,
                MALE_STUDENT_DATE_OF_BIRTH,
                Gender.MALE
        );
    }

    public static Student notAddedFemaleStudent() {
        return new Student(
                null,
                FEMALE_STUDENT_FIRSTNAME,
                FEMALE_STUDENT_LASTNAME,
                FEMALE_STUDENT_DATE_OF_BIRTH,
                Gender.FEMALE
        );
    }

    public static StudentDto notAddedFemaleStudentDto() {
        return new StudentDto(
                null,
                FEMALE_STUDENT_FIRSTNAME,
                FEMALE_STUDENT_LASTNAME,
                FEMALE_STUDENT_DATE_OF_BIRTH,
                Gender.FEMALE
        );
    }

    public static Student addedMaleStudent(UUID id) {
        return new Student(
                id,
                MALE_STUDENT_FIRSTNAME,
                MALE_STUDENT_LASTNAME,
                MALE_STUDENT_DATE_OF_BIRTH,
                Gender.MALE
        );
    }

    public static StudentDto addedMaleStudentDto(UUID id) {
        return new StudentDto(
                id,
                MALE_STUDENT_FIRSTNAME,
                MALE_STUDENT_LASTNAME,
                MALE_STUDENT_DATE_OF_BIRTH,
                Gender.MALE
        );
    }

    public static Student addedFemaleStudent(UUID id) {
        return new Student(
                id,
                FEMALE_STUDENT_FIRSTNAME,
                FEMALE_STUDENT_LASTNAME,
                FEMALE_STUDENT_DATE_OF_BIRTH,
                Gender.FEMALE
        );
    }

    public static StudentDto addedFemaleStudentDto(UUID id) {
        return new StudentDto(
                id,
                FEMALE_STUDENT_FIRSTNAME,
                FEMALE_STUDENT_LASTNAME,
                FEMALE_STUDENT_DATE_OF_BIRTH,
                Gender.FEMALE
        );
    }

}
