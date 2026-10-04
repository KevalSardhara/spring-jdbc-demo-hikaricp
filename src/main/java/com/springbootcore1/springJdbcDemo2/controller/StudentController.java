package com.springbootcore1.springJdbcDemo2.controller;


import com.springbootcore1.springJdbcDemo2.model.Student;
import com.springbootcore1.springJdbcDemo2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        String studentCreated = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.OK).body(studentCreated);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@RequestBody Student student, @PathVariable Long id) {
        String studentUpdated = studentService.updateStudent(student, id);
        return ResponseEntity.status(HttpStatus.OK).body(studentUpdated);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Student>> allStudentList() {
        List<Student> allStudentList = studentService.findAllStudent();
        if (allStudentList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(allStudentList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
        String studentDeleted = studentService.deleteStudentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentDeleted);
    }
}
