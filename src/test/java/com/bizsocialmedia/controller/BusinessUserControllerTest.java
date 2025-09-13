package com.bizsocialmedia.controller;

import com.bizsocialmedia.config.AbstractIntegrationTest;
import com.bizsocialmedia.entity.BusinessUser;
import com.bizsocialmedia.repository.BusinessUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureWebMvc
@Transactional
class BusinessUserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BusinessUserRepository businessUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        businessUserRepository.deleteAll();
    }

    @Test
    void testCreateBusinessUser() throws Exception {
        BusinessUser user = new BusinessUser();
        user.setCompanyName("Test Company");
        user.setEmail("test@company.com");
        user.setDescription("A test company");

        mockMvc.perform(post("/api/business-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName", is("Test Company")))
                .andExpect(jsonPath("$.email", is("test@company.com")))
                .andExpect(jsonPath("$.description", is("A test company")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    void testGetAllBusinessUsers() throws Exception {
        // Create test data
        BusinessUser user1 = new BusinessUser("Company 1", "user1@company.com", "Description 1");
        BusinessUser user2 = new BusinessUser("Company 2", "user2@company.com", "Description 2");
        businessUserRepository.save(user1);
        businessUserRepository.save(user2);

        mockMvc.perform(get("/api/business-users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetBusinessUserById() throws Exception {
        BusinessUser user = new BusinessUser("Test Company", "test@company.com", "Test Description");
        BusinessUser saved = businessUserRepository.save(user);

        mockMvc.perform(get("/api/business-users/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())))
                .andExpect(jsonPath("$.companyName", is("Test Company")))
                .andExpect(jsonPath("$.email", is("test@company.com")));
    }
}