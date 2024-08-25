package com.ivanalimin.spring_mvc_json_view.service;

import com.ivanalimin.spring_mvc_json_view.exception_handling.NotFoundException;
import com.ivanalimin.spring_mvc_json_view.model.User;
import com.ivanalimin.spring_mvc_json_view.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindAll() {
        User user1 = new User(UUID.randomUUID(), "John Doe", "john.doe@example.com", new HashSet<>());
        User user2 = new User(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", new HashSet<>());

        List<User> users = Arrays.asList(user1, user2);

        given(userRepository.findAll()).willReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user1.getName(), result.get(0).getName());
        assertEquals(user2.getEmail(), result.get(1).getEmail());
    }

    @Test
    void testFindById() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John Doe", "john.doe@example.com", new HashSet<>());

        given(userRepository.getWithOrdersById(userId)).willReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testFindById_UserNotFound() {
        UUID userId = UUID.randomUUID();

        given(userRepository.getWithOrdersById(userId)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    void testSave() {
        User user = new User(null, "John Doe", "john.doe@example.com", new HashSet<>());

        given(userRepository.save(user)).willReturn(user);

        User result = userService.save(user);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    void testUpdate() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User(userId, "John Doe", "john.doe@example.com", new HashSet<>());
        User updatedUser = new User(userId, "John Doe Updated", "john.doe.updated@example.com", new HashSet<>());

        given(userRepository.findById(userId)).willReturn(Optional.of(existingUser));
        given(userRepository.save(existingUser)).willReturn(updatedUser);

        User result = userService.update(userId, updatedUser);

        assertNotNull(result);
        assertEquals("John Doe Updated", result.getName());
        assertEquals("john.doe.updated@example.com", result.getEmail());
    }

    @Test
    void testUpdate_UserNotFound() {
        UUID userId = UUID.randomUUID();
        User updatedUser = new User(userId, "John Doe Updated", "john.doe.updated@example.com", new HashSet<>());

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(userId, updatedUser));
    }

    @Test
    void testDeleteById() {
        UUID userId = UUID.randomUUID();

        userService.deleteById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
