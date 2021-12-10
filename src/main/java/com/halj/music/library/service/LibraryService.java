package com.halj.music.library.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.halj.music.library.model.elastic.Album;
import com.halj.music.library.repository.elastic.AlbumRepository;

/**
 * The Class LibraryService.
 * Service to manage the music libraries of users
 */
@Service
public class LibraryService {

    /** The user service. */
    private UserService userService;

    /** The album repository. */
    private AlbumRepository albumRepository;

    /**
     * Instantiates a new library service.
     *
     * @param userService the user service
     * @param albumRepository the album repository
     */
    public LibraryService(UserService userService, AlbumRepository albumRepository) {
        super();
        this.userService = userService;
        this.albumRepository = albumRepository;
    }

    /**
     * Adds the album.
     *
     * @param albumId the album id
     * @param userId the user id
     */
    @Transactional
    public void addAlbum(UUID albumId, Long userId) {
        this.userService.getUser(userId).getAlbumIds().add(albumId);
    }

    /**
     * Adds the albums.
     *
     * @param albumIds the album ids
     * @param userId the user id
     */
    @Transactional
    public void addAlbums(List<UUID> albumIds, Long userId) {
        this.userService.getUser(userId).getAlbumIds().addAll(albumIds);
    }

    /**
     * Removes the album.
     *
     * @param albumId the album id
     * @param userId the user id
     */
    @Transactional
    public void removeAlbum(UUID albumId, Long userId) {
        this.userService.getUser(userId).getAlbumIds().remove(albumId);
    }

    /**
     * Removes the albums.
     *
     * @param albumIds the album ids
     * @param userId the user id
     */
    @Transactional
    public void removeAlbums(List<UUID> albumIds, Long userId) {
        this.userService.getUser(userId).getAlbumIds().removeAll(albumIds);
    }

    /**
     * Clear albums.
     *
     * @param userId the user id
     */
    @Transactional
    public void clearAlbums(Long userId) {
        this.userService.getUser(userId).getAlbumIds().clear();
    }

    /**
     * Gets the albums.
     *
     * @param userId the user id
     * @return the albums
     */
    public Iterable<Album> getAlbums(Long userId) {
        Set<UUID> albumIds = this.userService.getUser(userId).getAlbumIds();
        return this.albumRepository.findAllById(albumIds);
    }

}
