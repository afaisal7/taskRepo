package com.mentorship.mentorship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;
    private String status;
    @FutureOrPresent(message = "From date must be in the present or future")
    private LocalDateTime fromDate;
    @FutureOrPresent(message = "To date must be in the present or future")
    private LocalDateTime toDate;
    @NotNull(message = "Owner is required")
    private Long ownerId;
    private UserDto owner;
}
