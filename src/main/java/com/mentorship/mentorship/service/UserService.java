package com.mentorship.mentorship.service;

import com.mentorship.mentorship.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
}