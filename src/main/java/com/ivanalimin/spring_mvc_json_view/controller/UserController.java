package com.ivanalimin.spring_mvc_json_view.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ivanalimin.spring_mvc_json_view.model.User;
import com.ivanalimin.spring_mvc_json_view.model.Views;
import com.ivanalimin.spring_mvc_json_view.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    static final String REST_URL = "/rest/users";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
