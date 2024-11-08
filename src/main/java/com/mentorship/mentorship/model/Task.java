package com.mentorship.mentorship.model;

import com.mentorship.mentorship.util.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long ownerId;
}
