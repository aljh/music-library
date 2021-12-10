package com.halj.music.library.web;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.halj.music.library.model.elastic.Album;
import com.halj.music.library.service.AlbumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The Class AlbumController.
 */
@Tag(name = "Album management", description = "Endpoints for managing albums")
@RestController
@RequestMapping("/albums")
public class AlbumController {

    /** The album service. */
    @Autowired
    AlbumService albumService;

    /**
     * Adds the albums.
     *
     * @param albums the albums
     * @return the response entity
     */
    @Operation(summary = "Add a collection of albums to global library")
    @PostMapping
    public ResponseEntity<Iterable<Album>> addAlbums(@RequestBody List<@Valid Album> albums) {

        Iterable<Album> saved = this.albumService.saveAlbums(albums);

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/")
                .build()
                .toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    /**
     * Adds the album.
     *
     * @param uuid the uuid
     * @param album the album
     * @return the response entity
     */
    @Operation(summary = "Add or update an album in global library")
    @PutMapping(path = "/{uuid}")
    public ResponseEntity<Album> saveAlbum(@PathVariable UUID uuid, @Valid @RequestBody Album album) {

        album.setId(uuid); // if album had a UUID, it will be overridden to be coherent with uri's parameter

        Album saved = this.albumService.saveAlbum(album);

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{uuid}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    /**
     * Removes the albums.
     *
     * @param albums the albums
     * @return the response entity
     */
    @Operation(summary = "Remove set of albums from global library")
    @DeleteMapping
    public ResponseEntity<Void> removeAlbums(@RequestBody List<UUID> uuids) {

        this.albumService.deleteAlbums(uuids);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Clear (remove all albums) global library")
    @DeleteMapping(path = "/all")
    public ResponseEntity<Void> removeAllAlbums() {

        this.albumService.deleteAllAlbums();

        return ResponseEntity.noContent().build();
    }

    /**
     * Removes the album.
     *
     * @param albumId the album id
     * @return the response entity
     */
    @Operation(summary = "Delete an album from global library")
    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<Album> removeAlbum(@PathVariable UUID uuid) {

        this.albumService.deleteAlbum(uuid);

        return ResponseEntity.noContent().build();
    }

    /**
     * Gets the album.
     *
     * @param uuid the uuid
     * @return the album
     */
    @Operation(summary = "Retrieve an album from global library")
    @GetMapping(path = "/{uuid}")
    public ResponseEntity<Album> getAlbum(@PathVariable UUID uuid) {

        Optional<Album> optionalAlbum = this.albumService.getAlbum(uuid);

        if (optionalAlbum.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalAlbum.get());
    }

    @Operation(summary = "Free text search albums in global library")
    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<Album>> getAllAlbums() {

        Iterable<Album> results = this.albumService.getAll();

        return ResponseEntity.ok(results);
    }

    /**
     * Search albums.
     *
     * @param query the query
     * @return the response entity
     */
    @Operation(summary = "Free text search albums in global library")
    @PostMapping(path = "/search")
    public ResponseEntity<Iterable<Album>> searchAlbums(@RequestBody String query) {

        List<Album> results = this.albumService.freeTextSearch(query);

        return ResponseEntity.ok(results);
    }

}
