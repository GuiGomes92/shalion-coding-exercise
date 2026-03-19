package com.example.demoapi.Controller;

import com.example.demoapi.Service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demoapi.Models.School;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schools")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // Get all schools.
     
    @GetMapping("")
    public List<School> getSchools(@RequestParam(required = false) String name) {
        if (name != null) {
            return schoolService.getSchoolByName(name)
                    .map(List::of)
                    .orElse(List.of());
        } else {
            return schoolService.getAllSchool();
        }
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addSchool(@RequestBody School school) {
        schoolService.saveSchool(school);
    }
    
}