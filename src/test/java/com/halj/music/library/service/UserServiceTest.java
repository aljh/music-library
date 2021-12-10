package com.halj.music.library.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.halj.music.library.exception.UserNotFoundException;
import com.halj.music.library.repository.UserRepository;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        @Primary
        public UserService userService(UserRepository userRepository) {
            return new UserService(userRepository);
        }

    }

    // Mock repositories - we are no testing that repositories work
    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void test_delete_exceptionThrownWhenUserNotExist() {
        Mockito.when(this.userRepository.existsById(any())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> this.userService.deleteUser(1l));
    }

    @Test
    void test_get_exceptionThrownWhenUserNotExist() {
        Mockito.when(this.userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> this.userService.getUser(1l));
    }

}
