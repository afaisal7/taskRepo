package com.mentorship.mentorship.service.impl;

import com.mentorship.mentorship.exception.ResourceNotFoundException;
import com.mentorship.mentorship.mapper.UserMapper;
import com.mentorship.mentorship.model.User;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        userMapper.mapUserForUpdate(existingUser, user);
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
