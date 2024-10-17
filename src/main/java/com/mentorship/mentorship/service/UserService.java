package com.mentorship.mentorship.service;

import com.mentorship.mentorship.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    User getUserById(Long id);
    Page<User> getAllUsers(Pageable pageable);
    void deleteUser(Long id);
}