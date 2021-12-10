package com.halj.music.library.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.halj.music.library.model.elastic.Album;
import com.halj.music.library.service.LibraryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The Class LibraryController.
 */
@Tag(name = "Users libraries management", description = "Endpoints for managing users libraries")
@RestController
@RequestMapping("/users")
public class LibraryController {

    /** The library service. */
    @Autowired
    LibraryService libraryService;

    /**
     * Gets the albums.
     *
     * @param userId the user id
     * @return the albums
     */
    @Operation(summary = "Get user library")
    @GetMapping(path = "/{userId}/albums")
    public ResponseEntity<Iterable<Album>> getAlbums(@PathVariable Long userId) {

        return ResponseEntity.ok(this.libraryService.getAlbums(userId));
    }

    /**
     * Adds the albums.
     *
     * @param userId the user id
     * @param libraryItems the library items
     * @return the response entity
     */
    @Operation(summary = "Add one or several albums to user library")
    @PutMapping(path = "/{userId}/albums")
    public ResponseEntity<Iterable<Album>> addAlbums(@PathVariable Long userId, @RequestBody List<UUID> albumIds) {

        this.libraryService.addAlbums(albumIds, userId);

        return ResponseEntity.ok(this.libraryService.getAlbums(userId)); // return updated albums list
    }

    /**
     * Removes the albums.
     *
     * @param userId the user id
     * @param libraryItems the library items
     * @return the response entity
     */
    @Operation(summary = "Remove one or several albums from user library")
    @DeleteMapping(path = "/{userId}/albums")
    public ResponseEntity<Iterable<Album>> removeAlbums(@PathVariable Long userId, @RequestBody List<UUID> albumIds) {

        this.libraryService.removeAlbums(albumIds, userId);

        return ResponseEntity.ok(this.libraryService.getAlbums(userId)); // return updated albums list
    }

    /**
     * Clear albums.
     *
     * @param userId the user id
     * @return the response entity
     */
    @Operation(summary = "Reset user library")
    @DeleteMapping(path = "/{userId}/albums/all")
    public ResponseEntity<Void> clearAlbums(@PathVariable Long userId) {

        this.libraryService.clearAlbums(userId);

        return ResponseEntity.noContent().build();
    }

}
