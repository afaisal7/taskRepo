package com.mentorship.mentorship.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long ownerId;
}
