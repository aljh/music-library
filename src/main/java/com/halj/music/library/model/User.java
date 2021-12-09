package com.halj.music.library.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class User.
 * Represents a User entity
 */
@Entity
public class User {

    /** The id. */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** The name. */
    @NotBlank
    @Column
    private String name;

    /** The email. */
    @NotNull
    @Email(message = "Email is not valid", regexp = "^(.+)@(\\S+)$") // valid email contains '@'
    @Column(unique = true)
    private String email;

    /** The album ids. */
    @ElementCollection
    @CollectionTable(name = "user_library", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UUID> albumIds = new HashSet<>();

    /**
     * Instantiates a new user.
     */
    public User() {
        super();
    }

    /**
     * Instantiates a new user.
     *
     * @param name the name
     * @param email the email
     */
    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }

    /**
     * Instantiates a new user.
     *
     * @param id the id
     * @param name the name
     * @param email the email
     */
    public User(long id, String name, String email) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.albumIds = new HashSet<>();
    }

    /**
     * Instantiates a new user.
     *
     * @param id the id
     * @param name the name
     * @param email the email
     * @param albumIds the album ids
     */
    public User(long id, String name, String email, Set<UUID> albumIds) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.albumIds = albumIds;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the album ids.
     *
     * @return the album ids
     */
    public Set<UUID> getAlbumIds() {
        return this.albumIds;
    }

    /**
     * Sets the album ids.
     *
     * @param albumIds the new album ids
     */
    public void setAlbumIds(Set<UUID> albumIds) {
        this.albumIds = albumIds;
    }

}
