package com.example.demoapi.Models;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne
    @JoinColumn(name = "students_id")
    private Student student;

    @Enumerated(EnumType.STRING)
    private ConfirmationStatus confirmationStatus;

    public Enrollment(School school, Student student, ConfirmationStatus confirmationStatus) {
        setSchool(school);
        setStudent(student);
        setConfirmationStatus(confirmationStatus);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public School getSchool() {
        return school;
    }

    public Student getStudent() {
        return student;
    }

    public ConfirmationStatus getConfirmationStatus() {
        return confirmationStatus;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setConfirmationStatus(ConfirmationStatus confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

}
