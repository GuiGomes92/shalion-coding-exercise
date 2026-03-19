package com.example.demoapi.Controller;

import com.example.demoapi.Models.School;
import com.example.demoapi.Repositories.SchoolRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
public class SchoolControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    SchoolRepository schoolRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private School school;
    private School school1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper.registerModule(new JavaTimeModule());

        school = new School(null,"Biblos", 100);
        school1 = new School(null,"MV1", 100);
        schoolRepository.save(school);
        schoolRepository.save(school1);
    }

    @AfterEach
    void tearDown() {
        schoolRepository.deleteAll();
    }

    @Test
    @DisplayName("It is possible to get all schools")
    void getSchools() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schools"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andReturn();
        School[] schoolRetrieved = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), School[].class);
        assertEquals(2, schoolRetrieved.length);
        assertEquals("Biblos", schoolRetrieved[0].getName());
    }

    @Test
    @DisplayName("It should get school by name")
    void getSchoolByName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schools").param("name", "Biblos"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andReturn();
        School[] schoolRetrieved = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), School[].class);
        assertEquals(1, schoolRetrieved.length);
        assertEquals("Biblos", schoolRetrieved[0].getName());
    }

    @Test
    @DisplayName("It should throw error if not found")
    void throwsErrorWrongName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/schools").param("name", "NonExistent"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("School not found")).andReturn();
    }

}
