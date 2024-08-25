package com.ivanalimin.spring_mvc_json_view.service;

import com.ivanalimin.spring_mvc_json_view.exception_handling.NotFoundException;
import com.ivanalimin.spring_mvc_json_view.model.User;
import com.ivanalimin.spring_mvc_json_view.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.getWithOrdersById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(UUID id, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User for update cannot be null");
        }
        User userToUpdate = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id " + id + " not found"));
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setOrders(user.getOrders());
        return userRepository.save(userToUpdate);
    }

    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
