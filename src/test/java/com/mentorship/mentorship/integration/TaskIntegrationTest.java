package com.mentorship.mentorship.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentorship.mentorship.model.Task;
import com.mentorship.mentorship.model.User;
import com.mentorship.mentorship.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    private Task task;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setFirstName("Ahmed");
        user.setLastName("Faisal");
        user.setAge(29);
        user.setDesignation("Developer");
        task = new Task();
        task.setTitle("Sample Task");
        task.setDescription("Task Description");
        task.setStatus(Status.READY);
        task.setFromDate(LocalDateTime.now());
        task.setToDate(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testCreateTask() throws Exception {
        User createdUser = createUser(user);
        mockMvc.perform(post("/tasks?userId=" + createdUser.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(task))).andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("Sample Task"));
    }

    @Test
    void testGetTaskById() throws Exception {
        User createdUser = createUser(user);
        Task createdTask = createTask(createdUser.getId(), task);
        mockMvc.perform(get("/tasks/" + createdTask.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.description").value("Task Description"));
    }

    @Test
    void testUpdateTask() throws Exception {
        User createdUser = createUser(user);
        Task createdTask = createTask(createdUser.getId(), task);
        createdTask.setTitle("Updated Task");
        mockMvc.perform(put("/tasks/" + createdTask.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createdTask))).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void testDeleteTask() throws Exception {
        User createdUser = createUser(user);
        Task createdTask = createTask(createdUser.getId(), task);
        mockMvc.perform(delete("/tasks/" + createdTask.getId())).andExpect(status().isNoContent());
    }

    private User createUser(User user) throws Exception {
        String response = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, User.class);
    }

    private Task createTask(Long userId, Task task) throws Exception {
        String response = mockMvc.perform(post("/tasks?userId=" + userId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(task))).andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, Task.class);
    }
}
