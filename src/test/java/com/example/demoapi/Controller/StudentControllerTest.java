package com.example.demoapi.Controller;

import com.example.demoapi.Models.School;
import com.example.demoapi.Models.Student;
import com.example.demoapi.Repositories.SchoolRepository;
import com.example.demoapi.Repositories.StudentRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class StudentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    StudentRepository studentRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private Student student;
    private Student student1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper.registerModule(new JavaTimeModule());

        student = new Student(null, "Guilherme");
        student1 = new Student(null, "William");
        studentRepository.save(student);
        studentRepository.save(student1);
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("It is possible to get all students")
    void getStudents() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andReturn();
        Student[] studentsRetrieved = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Student[].class);
        assertEquals(2, studentsRetrieved.length);
        assertEquals("Guilherme", studentsRetrieved[0].getName());
    }

    @Test
    @DisplayName("It should get student by name")
    void getSchoolByName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students").param("name", "William"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andReturn();
        School[] schoolRetrieved = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), School[].class);
        assertEquals(1, schoolRetrieved.length);
        assertEquals("William", schoolRetrieved[0].getName());
    }

    @Test
    @DisplayName("It should throw error if not found")
    void throwsErrorWrongName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students").param("name", "NonExistent"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Student not found")).andReturn();
    }
}
