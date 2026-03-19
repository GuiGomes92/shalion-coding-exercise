package com.example.demoapi.Controller;

import com.example.demoapi.Models.School;
import com.example.demoapi.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demoapi.Models.Student;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Get all students.

    @GetMapping("")
    public List<Student> getStudents(@RequestParam(required = false) String name) {
        if (name != null) {
            return studentService.getStudentByName(name)
                    .map(List::of)
                    .orElse(List.of());
        } else {
            return studentService.getAllStudents();
        }
    }
    
}