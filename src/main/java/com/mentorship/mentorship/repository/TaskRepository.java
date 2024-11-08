package com.mentorship.mentorship.repository;

import com.mentorship.mentorship.model.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
    @Modifying
    @Query("delete from task where id=:id")
    Mono<Boolean> deleteTaskById(Long id);

    Flux<Task> findBy(Pageable pageable);
}
