package com.halj.music.library.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.halj.music.library.model.User;
import com.halj.music.library.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The Class UserController.
 */
@Tag(name = "User management", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {

    /** The user service. */
    @Autowired
    UserService userService;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok(this.userService.getAll());
    }

    /**
     * Gets the user.
     *
     * @param userId the user id
     * @return the user
     */
    @Operation(summary = "Find a user")
    @GetMapping(path = "/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.userService.getUser(userId));
    }

    /**
     * Creates the user.
     *
     * @param user the user
     * @return the response entity
     */
    @Operation(summary = "Create a user")
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        User saved = this.userService.createUser(user);

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    /**
     * Updates the user.
     *
     * @param userId the user id
     * @param user the user
     * @return the response entity
     */
    @Operation(summary = "Update an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody User user) {

        User saved = this.userService.saveUser(userId, user);

        return ResponseEntity.ok(saved);
    }

    /**
     * Delete user.
     *
     * @param userId the user id
     * @return the response entity
     */
    @Operation(summary = "Delete a user")
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {

        this.userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Clear users.
     *
     * @return the response entity
     */
    @Operation(summary = "Delete all users")
    @DeleteMapping
    public ResponseEntity<Void> clearUsers() {

        this.userService.deleteAll();

        return ResponseEntity.noContent().build();
    }

}
