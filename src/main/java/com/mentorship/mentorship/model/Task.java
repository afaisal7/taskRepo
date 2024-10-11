package com.mentorship.mentorship.model;

import com.mentorship.mentorship.util.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    @ManyToOne
    private User owner;
}
