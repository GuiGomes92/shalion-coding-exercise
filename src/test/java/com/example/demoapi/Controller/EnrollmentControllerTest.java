package com.example.demoapi.Controller;

import com.example.demoapi.DTO.EnrollmentDTO;
import com.example.demoapi.Exception.EnrollmentNotFoundException;
import com.example.demoapi.Models.ConfirmationStatus;
import com.example.demoapi.Models.Enrollment;
import com.example.demoapi.Models.School;
import com.example.demoapi.Models.Student;
import com.example.demoapi.Repositories.EnrollmentRepository;
import com.example.demoapi.Repositories.SchoolRepository;
import com.example.demoapi.Repositories.StudentRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EnrollmentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SchoolRepository schoolRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private School school;
    private Student student;
    private Student student1;
    private Enrollment enrollment;
    private Enrollment enrollment1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        school = new School(null, "Biblos", 100);
        schoolRepository.save(school);

        student = new Student(null, "Alice");
        student1 = new Student(null, "Bob");
        studentRepository.save(student);
        studentRepository.save(student1);

        enrollment = new Enrollment(school, student, ConfirmationStatus.PENDING);
        enrollment1 = new Enrollment(school, student1, ConfirmationStatus.PENDING);
        enrollmentRepository.save(enrollment);
        enrollmentRepository.save(enrollment1);
    }

    @AfterEach
    void tearDown() {
        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();
        schoolRepository.deleteAll();
    }

    @Test
    @DisplayName("It should get enrollment by id")
    void getEnrollmentById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/enrollments/" + enrollment.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andReturn();

        EnrollmentDTO enrollmentRetrieved = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), EnrollmentDTO.class);

        assertEquals(student.getId(), enrollmentRetrieved.getStudentId());
        assertEquals(school.getId(), enrollmentRetrieved.getSchoolId());
        assertEquals(ConfirmationStatus.PENDING, enrollmentRetrieved.getConfirmationStatus());
    }

    @Test
    @DisplayName("It should throw error if enrollment not found")
    void throwsErrorWhenNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/9999"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(
                        EnrollmentNotFoundException.class,
                        result.getResolvedException()
                ))
                .andExpect(result -> assertEquals(
                        "Enrollment not found",
                        result.getResolvedException().getMessage()
                ));
    }

    @Test
    @DisplayName("It should update enrollment status")
    void updateEnrollmentStatus() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.patch("/enrollments/" + enrollment.getId() + "/status")
                                .param("newStatus", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andReturn();

        EnrollmentDTO updatedEnrollment = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), EnrollmentDTO.class);

        assertEquals(ConfirmationStatus.ACTIVE, updatedEnrollment.getConfirmationStatus());
    }

    @Test
    @DisplayName("It should throw error when updating status with invalid value")
    void throwsErrorOnInvalidStatus() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/enrollments/" + enrollment.getId() + "/status")
                                .param("newStatus", "INVALID_STATUS"))
                .andExpect(status().isBadRequest());
    }
}
