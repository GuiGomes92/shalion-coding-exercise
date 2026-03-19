package com.example.demoapi.Service;

import java.util.List;
import java.util.Optional;

import com.example.demoapi.Models.School;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demoapi.Models.Student;
import com.example.demoapi.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Optional<Student> getStudentByName(String name) {
        Optional<Student> studentRetrieved = studentRepository.findByName(name);
        if (studentRetrieved.isPresent()) {
            return studentRetrieved;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }
}
