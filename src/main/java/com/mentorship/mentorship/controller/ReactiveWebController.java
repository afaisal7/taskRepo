package com.mentorship.mentorship.controller;

import com.mentorship.mentorship.dto.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("reactive")
public class ReactiveWebController {
    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping("tasks")
    public Flux<TaskDto> getTasks() {
        return this.webClient.get()
                .uri("/tasks")
                .retrieve()
                .bodyToFlux(TaskDto.class)
                .doOnNext(p -> log.info("received: {}", p));
    }

    @GetMapping(value = "tasks/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TaskDto> getTasksStream() {
        return this.webClient.get()
                .uri("/tasks")
                .retrieve()
                .bodyToFlux(TaskDto.class)
                .doOnNext(p -> log.info("received: {}", p));
    }

}
