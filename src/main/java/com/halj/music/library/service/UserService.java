package com.halj.music.library.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.halj.music.library.exception.UserNotFoundException;
import com.halj.music.library.model.User;
import com.halj.music.library.repository.UserRepository;

/**
 * The Class UserService.
 * Service to manage Users
 */
@Service
public class UserService {

    /** The user repository. */
    private UserRepository userRepository;

    /**
     * Instantiates a new user service.
     *
     * @param userRepository the user repository
     */
    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    /**
     * Gets the user.
     *
     * @param userId the user id
     * @return the user
     */
    public User getUser(Long userId) {
        Optional<User> user = this.userRepository.findById(userId);

        return user.orElseThrow(() -> new UserNotFoundException("No user found matching id " + userId));
    }

    /**
     * Gets the all.
     *
     * @return the all
     */
    public Iterable<User> getAll() {
        return this.userRepository.findAll();
    }

    /**
     * Creates the user.
     *
     * @param user the user
     * @return the user
     */
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * Save user.
     *
     * @param userId the user id
     * @param user the user
     * @return the user
     */
    public User saveUser(Long userId, User user) {
        checkUserExist(userId);

        User updated = new User(userId,
                user.getName(),
                user.getEmail(),
                user.getAlbumIds());

        return this.userRepository.save(updated);
    }

    /**
     * Delete user.
     *
     * @param userId the user id
     */
    public void deleteUser(Long userId) {
        checkUserExist(userId);
        this.userRepository.deleteById(userId);
    }

    /**
     * Delete all.
     */
    public void deleteAll() {
        this.userRepository.deleteAll();
    }

    /**
     * Check user exist.
     *
     * @param userId the user id
     */
    private void checkUserExist(Long userId) {
        if (!this.userRepository.existsById(userId)) {
            throw new UserNotFoundException("No user found matching id " + userId);
        }
    }
}
