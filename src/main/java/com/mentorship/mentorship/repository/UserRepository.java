package com.mentorship.mentorship.repository;

import com.mentorship.mentorship.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
