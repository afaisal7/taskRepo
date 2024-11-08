package com.mentorship.mentorship.integration;

import com.mentorship.mentorship.dto.TaskDto;
import com.mentorship.mentorship.util.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureTestDatabase
class TaskControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateTask() {
        TaskDto taskDto = new TaskDto(null, "Important Task", "A detailed task description", Status.READY.name(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
                34L, null);

        webTestClient.post().uri("/tasks?userId=34")
                .bodyValue(taskDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class)
                .value(response -> assertThat(response.getId()).isNotNull())
                .value(response -> assertThat(response.getTitle()).isEqualTo("Important Task"))
                .value(response -> assertThat(response.getDescription()).isEqualTo("A detailed task description"))
                .value(response -> assertThat(response.getOwnerId()).isEqualTo(34L));
    }

    @Test
    void testGetTaskById() {
        // Assuming a task with ID 1 exists
        webTestClient.get().uri("/tasks/15")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class)
                .value(response -> assertThat(response.getId()).isEqualTo(15L))
                .value(response -> assertThat(response.getTitle()).isEqualTo("New Task Title"))
                .value(response -> assertThat(response.getOwnerId()).isEqualTo(34L));
    }

    @Test
    void testUpdateTask() {
        // Step 1: Create a task
        TaskDto taskDto = new TaskDto(null, "Initial Task", "Task description", Status.IN_PROGRESS.name(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
                34L, null);

        TaskDto createdTask = webTestClient.post().uri("/tasks?userId=1")
                .bodyValue(taskDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class)
                .returnResult()
                .getResponseBody();

        // Step 2: Update the task
        assert createdTask != null;
        TaskDto updatedTask = new TaskDto(createdTask.getId(), "Updated Task", "Updated description",
                "COMPLETED", createdTask.getFromDate(),
                createdTask.getToDate(), createdTask.getOwnerId(), null);

        webTestClient.put().uri("/tasks/" + createdTask.getId())
                .bodyValue(updatedTask)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class)
                .value(response -> {
                    assertThat(response.getTitle()).isEqualTo("Updated Task");
                    assertThat(response.getDescription()).isEqualTo("Updated description");
                });
    }

    @Test
    void testDeleteTask() {
        TaskDto taskDto = new TaskDto(null, "Task to Delete", "Description", Status.IN_PROGRESS.name(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2),
                34L, null);
        webTestClient.post().uri("/tasks?userId=34")
                .bodyValue(taskDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class)
                .value(response -> {
                    assertThat(response.getId()).isNotNull(); // Check that the user is created
                    Long taskId = response.getId();
                    webTestClient.delete().uri("/tasks/" + taskId)
                            .exchange()
                            .expectStatus().isOk();
                });
    }


    @Test
    void testGetAllTasks() {
        webTestClient.get().uri("/tasks?page=1&size=3")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskDto.class)
                .value(tasks -> assertThat(tasks).hasSize(3));
    }
}

