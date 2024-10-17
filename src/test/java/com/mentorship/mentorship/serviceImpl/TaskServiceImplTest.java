package com.mentorship.mentorship.serviceImpl;

import com.mentorship.mentorship.mapper.TaskMapper;
import com.mentorship.mentorship.model.Task;
import com.mentorship.mentorship.model.User;
import com.mentorship.mentorship.repository.TaskRepository;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.impl.TaskServiceImpl;
import com.mentorship.mentorship.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskMapper taskMapper;


    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(1L);
        task.setTitle("Sample Task");
        task.setDescription("Task Description");
        task.setStatus(Status.READY);
        task.setFromDate(LocalDateTime.now());
        task.setToDate(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testCreateTask() {
        User owner = new User();
        owner.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task createdTask = taskService.createTask(task, 1L);
        assertEquals("Sample Task", createdTask.getTitle());
        assertEquals(owner, createdTask.getOwner());
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task foundTask = taskService.getTaskById(1L);
        assertEquals("Sample Task", foundTask.getTitle());
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Collections.singletonList(task);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> taskPage = new PageImpl<>(tasks, pageable, tasks.size());
        when(taskRepository.findAll(pageable)).thenReturn(taskPage);
        Page<Task> resultPage = taskService.getAllTasks(pageable);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(task, resultPage.getContent().get(0));
    }

    @Test
    void testUpdateTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        task.setTitle("Updated Task");
        Task updatedTask = taskService.updateTask(1L, task);
        assertEquals("Updated Task", updatedTask.getTitle());
    }

    @Test
    void testDeleteTask() {
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}