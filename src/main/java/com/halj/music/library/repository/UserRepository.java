package com.halj.music.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.halj.music.library.model.User;

/**
 * The Interface UserRepository.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find by name.
     *
     * @param name the name
     * @return the list
     */
    List<User> findByName(@Param("name") String name);

    /**
     * Find by email.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(@Param("email") String email);

}
