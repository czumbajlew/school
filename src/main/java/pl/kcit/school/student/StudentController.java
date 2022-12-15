package pl.kcit.school.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    StudentDto getStudent(@PathVariable("id") long id) {
        return studentService.getStudent(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    StudentDto addStudent(@RequestBody StudentDto studentDto) {
        return studentService.addStudent(studentDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    StudentDto updateStudent(@PathVariable("id") long id, @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(id, studentDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
    }

}
