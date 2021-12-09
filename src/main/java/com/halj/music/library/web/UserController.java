package com.halj.music.library.web;

import java.net.URI;
import java.util.Optional;

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
import com.halj.music.library.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The Class UserController.
 */
@Tag(name = "User management", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {

    /** The user repository. */
    @Autowired
    UserRepository userRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<Iterable<User>> getAll() {
        return ResponseEntity.ok(this.userRepository.findAll());
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
        Optional<User> optionalUser = this.userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalUser.get());
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

        User saved = this.userRepository.save(user);

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    /**
     * Put.
     *
     * @param userId the user id
     * @param user the user
     * @return the response entity
     */
    @Operation(summary = "Update an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<User> put(@PathVariable("id") Long userId, @Valid @RequestBody User user) {
        if (!this.userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();

        } else {
            User saved = this.userRepository.save(
                    new User(userId,
                            user.getName(),
                            user.getEmail(),
                            user.getAlbumIds()));

            return ResponseEntity.ok(saved);
        }
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

        if (!this.userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        } else {
            this.userRepository.deleteById(userId);

            return ResponseEntity.noContent().build();
        }

    }

    /**
     * Clear users.
     *
     * @return the response entity
     */
    @Operation(summary = "Delete all users")
    @DeleteMapping
    public ResponseEntity<Void> clearUsers() {

        this.userRepository.deleteAll();

        return ResponseEntity.noContent().build();
    }

}
