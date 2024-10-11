package com.mentorship.mentorship.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentorship.mentorship.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setFirstName("Ahmed");
        user.setLastName("Faisal");
        user.setAge(29);
        user.setDesignation("Developer");
    }

    @Test
    void testCreateUser() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Ahmed"));
    }

    @Test
    void testGetUserById() throws Exception {
        User createdUser = createUser(user);
        mockMvc.perform(get("/users/" + createdUser.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.lastName").value("Faisal"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User createdUser = createUser(user);
        createdUser.setFirstName("Jane");
        mockMvc.perform(put("/users/" + createdUser.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createdUser))).andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void testDeleteUser() throws Exception {
        User createdUser = createUser(user);
        mockMvc.perform(delete("/users/" + createdUser.getId())).andExpect(status().isNoContent());
    }

    private User createUser(User user) throws Exception {
        String response = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, User.class);
    }
}