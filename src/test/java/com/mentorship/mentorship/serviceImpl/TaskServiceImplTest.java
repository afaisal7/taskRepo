package com.mentorship.mentorship.serviceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mentorship.mentorship.model.Task;
import com.mentorship.mentorship.repository.TaskRepository;
import com.mentorship.mentorship.service.impl.TaskServiceImpl;
import com.mentorship.mentorship.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

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
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task createdTask = taskService.createTask(task, 1L);
        assertEquals("Sample Task", createdTask.getTitle());
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task foundTask = taskService.getTaskById(1L);
        assertEquals("Sample Task", foundTask.getTitle());
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
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