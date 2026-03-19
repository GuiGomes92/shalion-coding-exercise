package com.example.demoapi.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demoapi.Models.School;
import com.example.demoapi.Repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

     
    public List<School> getAllSchool() {
        return schoolRepository.findAll();
    }

    public Optional<School> getSchoolByName(String name) {
        Optional<School> schoolRetrieved = schoolRepository.findByName(name);
        if (schoolRetrieved.isPresent()) {
            return schoolRetrieved;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found");
        }
    }

    public void updateSchool(Long id, School school) {
                Optional<School> schoolFromDb = schoolRepository.findById(id);
        if (schoolFromDb.isPresent()) {
            school.setId(schoolFromDb.get().getId());
            schoolRepository.save(school);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found");
        }
    }

    public void saveSchool(School school) {
        schoolRepository.save(school);
    }
}
