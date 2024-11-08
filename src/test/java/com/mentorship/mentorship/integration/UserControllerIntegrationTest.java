package com.mentorship.mentorship.integration;

import com.mentorship.mentorship.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateUser() {
        // Create a new UserDto
        UserDto userDto = new UserDto(null, "Ahmed", "Faisal", 30, "Developer");

        // Call the createUser endpoint
        webTestClient.post().uri("/users")
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(response -> assertThat(response.getId()).isNotNull()) // Check ID is generated
                .value(response -> assertThat(response.getFirstName()).isEqualTo("Ahmed")) // Validate the first name
                .value(response -> assertThat(response.getLastName()).isEqualTo("Faisal"));
    }

    @Test
    void testGetUserById() {
        UserDto userDto = new UserDto(null, "Ahmed", "Faisal", 30, "Developer");

        webTestClient.post().uri("/users")
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(response -> {
                    assertThat(response.getId()).isNotNull();
                    Long userId = response.getId();

                    // Step 2: Get the user by ID
                    webTestClient.get().uri("/users/" + userId)
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(UserDto.class)
                            .value(retrievedUser -> {
                                assertThat(retrievedUser.getId()).isEqualTo(userId); // Verify ID
                                assertThat(retrievedUser.getFirstName()).isEqualTo("Ahmed"); // Verify first name
                                assertThat(retrievedUser.getLastName()).isEqualTo("Faisal"); // Verify last name
                            });
                });
    }


    @Test
    void testGetAllUsers() {
        // Call the getAllUsers endpoint with default pagination
        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(users -> assertThat(users).isNotEmpty());
    }

    @Test
    void testUpdateUser() {
        // Step 1: Create a user first
        UserDto userDto = new UserDto(null, "Ahmed", "Faisal", 30, "Developer");

        webTestClient.post().uri("/users")
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(response -> {
                    assertThat(response.getId()).isNotNull(); // Check that the user is created
                    Long userId = response.getId(); // Capture the generated ID
                    UserDto updatedUserDto = new UserDto(null, "Ahmed", "Faisal", 35, "Senior Developer");

                    webTestClient.put().uri("/users/" + userId)
                            .bodyValue(updatedUserDto)
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(UserDto.class)
                            .value(updatedResponse -> assertThat(updatedResponse.getAge()).isEqualTo(35)) // Check updated age
                            .value(updatedResponse -> assertThat(updatedResponse.getDesignation()).isEqualTo("Senior Developer")); // Check updated designation
                });
    }


    @Test
    void testDeleteUser() {
        UserDto userDto = new UserDto(null, "Ahmed", "Faisal", 30, "Developer");

        webTestClient.post().uri("/users")
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(response -> {
                    assertThat(response.getId()).isNotNull(); // Check that the user is created
                    Long userId = response.getId();
                    webTestClient.delete().uri("/users/" + userId)
                            .exchange()
                            .expectStatus().isOk(); // Expect no content for successful deletion
                });
    }

}
