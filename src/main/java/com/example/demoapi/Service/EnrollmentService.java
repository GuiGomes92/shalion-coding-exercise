package com.example.demoapi.Service;

import com.example.demoapi.DTO.EnrollmentDTO;
import com.example.demoapi.Exception.EnrollmentNotFoundException;
import com.example.demoapi.Models.ConfirmationStatus;
import com.example.demoapi.Models.Enrollment;
import com.example.demoapi.Repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Return a DTO instead of the raw entity
    public EnrollmentDTO getEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found"));
        return new EnrollmentDTO(enrollment);
    }

    // Status update — takes only what's needed, returns DTO
    public EnrollmentDTO updateStatus(Long id, ConfirmationStatus newStatus) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollment.setConfirmationStatus(newStatus);
        enrollmentRepository.save(enrollment);
        return new EnrollmentDTO(enrollment);
    }
}
