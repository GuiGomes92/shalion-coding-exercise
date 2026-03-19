package com.example.demoapi.DTO;

import com.example.demoapi.Models.ConfirmationStatus;
import com.example.demoapi.Models.Enrollment;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnrollmentDTO {
    private Long id;
    private Long schoolId;
    private Long studentId;
    private ConfirmationStatus confirmationStatus;

    public EnrollmentDTO(Enrollment enrollment) {
        this.id = enrollment.getId();
        this.schoolId = enrollment.getSchool().getId();
        this.studentId = enrollment.getStudent().getId();
        this.confirmationStatus = enrollment.getConfirmationStatus();
    }

    public Long getId() { return id; }
    public Long getSchoolId() { return schoolId; }
    public Long getStudentId() { return studentId; }
    public ConfirmationStatus getConfirmationStatus() { return confirmationStatus; }
}