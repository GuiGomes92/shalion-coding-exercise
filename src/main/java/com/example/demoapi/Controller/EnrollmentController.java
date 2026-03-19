package com.example.demoapi.Controller;

import com.example.demoapi.DTO.EnrollmentDTO;
import com.example.demoapi.Models.ConfirmationStatus;
import com.example.demoapi.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/enrollments")
    public class EnrollmentController {

        @Autowired
        private EnrollmentService enrollmentService;

        @GetMapping("/{id}")
        public ResponseEntity<EnrollmentDTO> getEnrollment(@PathVariable Long id) {
            return ResponseEntity.ok(enrollmentService.getEnrollment(id));
        }

        @PatchMapping("/{id}/status")
        public ResponseEntity<EnrollmentDTO> updateStatus(
                @PathVariable Long id,
                @RequestParam ConfirmationStatus newStatus) {
            return ResponseEntity.ok(enrollmentService.updateStatus(id, newStatus));
        }
    }
