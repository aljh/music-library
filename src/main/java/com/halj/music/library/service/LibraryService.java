package com.halj.music.library.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.halj.music.library.dto.LibraryItemDTO;
import com.halj.music.library.exception.UserNotFoundException;
import com.halj.music.library.model.User;
import com.halj.music.library.model.elastic.Album;
import com.halj.music.library.repository.UserRepository;
import com.halj.music.library.repository.elastic.AlbumRepository;

/**
 * The Class LibraryService.
 * Service to manage the music libraries of users
 */
@Service
public class LibraryService {

    /** The user repository. */
    private UserRepository userRepository;

    /** The album repository. */
    private AlbumRepository albumRepository;

    /**
     * Instantiates a new library service.
     *
     * @param userRepository the user repository
     * @param albumRepository the album repository
     */
    public LibraryService(UserRepository userRepository, AlbumRepository albumRepository) {
        super();
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
    }

    /**
     * Adds the album.
     *
     * @param libraryItem the library item
     * @param userId the user id
     */
    @Transactional
    public void addAlbum(LibraryItemDTO libraryItem, Long userId) {
        getUser(userId).getAlbumIds().add(libraryItem.getAlbumId());
    }

    /**
     * Adds the albums.
     *
     * @param libraryItems the library items
     * @param userId the user id
     */
    @Transactional
    public void addAlbums(List<LibraryItemDTO> libraryItems, Long userId) {
        getUser(userId).getAlbumIds().addAll(toAlbumIds(libraryItems));
    }

    /**
     * Removes the album.
     *
     * @param libraryItem the library item
     * @param userId the user id
     */
    @Transactional
    public void removeAlbum(LibraryItemDTO libraryItem, Long userId) {
        getUser(userId).getAlbumIds().remove(libraryItem.getAlbumId());
    }

    /**
     * Removes the albums.
     *
     * @param libraryItems the library items
     * @param userId the user id
     */
    @Transactional
    public void removeAlbums(List<LibraryItemDTO> libraryItems, Long userId) {
        getUser(userId).getAlbumIds().removeAll(toAlbumIds(libraryItems));
    }

    /**
     * Clear albums.
     *
     * @param userId the user id
     */
    @Transactional
    public void clearAlbums(Long userId) {
        getUser(userId).getAlbumIds().clear();
    }

    /**
     * Gets the albums.
     *
     * @param userId the user id
     * @return the albums
     */
    public Iterable<Album> getAlbums(Long userId) {
        Set<UUID> albumIds = getUser(userId).getAlbumIds();
        return this.albumRepository.findAllById(albumIds);
    }

    /**
     * To album ids.
     *
     * @param libraryItems the library items
     * @return the list
     */
    private List<UUID> toAlbumIds(List<LibraryItemDTO> libraryItems) {
        return libraryItems.stream()
                .map(LibraryItemDTO::getAlbumId)
                .collect(Collectors.toList());
    }

    /**
     * Gets the user.
     *
     * @param userId the user id
     * @return the user
     */
    private User getUser(Long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else
            throw new UserNotFoundException("No user found matching id " + userId);
    }

}
