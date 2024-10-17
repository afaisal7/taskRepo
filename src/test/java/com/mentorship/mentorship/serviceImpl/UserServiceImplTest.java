package com.mentorship.mentorship.serviceImpl;

import com.mentorship.mentorship.mapper.UserMapper;
import com.mentorship.mentorship.model.Task;
import com.mentorship.mentorship.model.User;
import com.mentorship.mentorship.repository.UserRepository;
import com.mentorship.mentorship.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFirstName("Ahmed");
        user.setLastName("Faisal");
        user.setAge(29);
        user.setDesignation("Developer");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertEquals("Ahmed", createdUser.getFirstName());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);
        assertEquals("Faisal", foundUser.getLastName());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Collections.singletonList(user);
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        Page<User> resultPage = userService.getAllUsers(pageable);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(user, resultPage.getContent().get(0));
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.setFirstName("Jane");
        User updatedUser = userService.updateUser(1L, user);
        assertEquals("Jane", updatedUser.getFirstName());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
