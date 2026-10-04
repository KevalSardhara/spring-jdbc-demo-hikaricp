package com.springbootcore1.springJdbcDemo2.service;

import com.springbootcore1.springJdbcDemo2.model.Student;
import com.springbootcore1.springJdbcDemo2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public String createStudent(Student student) {
        studentRepository.createStudent(student);
        return "Student created successfully";
    }

    public String updateStudent(Student student, Long id) {
        studentRepository.updateStudent(student, id);
        return "Student updated successfully";
    }

    public Student getStudentById(Long id) {
        Student getStudent = studentRepository.getStudentById(id);
        return getStudent;
    }

    public List<Student> findAllStudent() {
        List<Student> allStudentList = studentRepository.findAllStudent();
        return allStudentList;
    }

    public String deleteStudentById(Long id) {
        studentRepository.deleteStudentById(id);
        return "Deleted successfully";
    }
}
