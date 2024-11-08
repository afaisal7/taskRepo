package com.mentorship.mentorship.controller;

import com.mentorship.mentorship.dto.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("traditional")
public class TraditionalWebController {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping("tasks")
    public List<TaskDto> getTasks() {
        var list = this.restClient.get()
                .uri("/tasks")
                .retrieve()
                .body(new ParameterizedTypeReference<List<TaskDto>>() {
                });
        log.info("received response: {}", list);
        return list;
    }

    @GetMapping("tasks2")
    public Flux<TaskDto> getTasks2() {
        var list = this.restClient.get()
                .uri("/tasks")
                .retrieve()
                .body(new ParameterizedTypeReference<List<TaskDto>>() {
                });
        log.info("received response: {}", list);
        return Flux.fromIterable(list);
    }

}
