package com.mentorship.mentorship.repository;

import com.mentorship.mentorship.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Modifying
    @Query("delete from user where id=:id")
    Mono<Boolean> deleteUserById(Long id);

    Flux<User> findBy(Pageable pageable);
}
