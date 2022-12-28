package pl.kcit.school.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(
        value = "/student",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
class StudentController {

    private final StudentService studentService;

    StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    StudentDto getStudent(@RequestParam("id") UUID id) {
        return studentService.getStudent(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    StudentDto addStudent(@RequestBody StudentDto studentDto) {
        return studentService.addStudent(studentDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    StudentDto updateStudent(@RequestParam("id") UUID id, @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(id, studentDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteStudent(@RequestParam("id") UUID id) {
        studentService.deleteStudent(id);
    }

}
