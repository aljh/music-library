package com.halj.music.library.dto;

import java.util.UUID;

/**
 * The Class LibraryItemDTO.
 * Represents a reference to an Album Entity in Elasticsearch
 */
public class LibraryItemDTO {

    /** The album id. */
    private UUID albumId;

    /**
     * Instantiates a new library item DTO.
     */
    public LibraryItemDTO() {
        super();
    }

    /**
     * Instantiates a new library item DTO.
     *
     * @param albumId the album id
     */
    public LibraryItemDTO(UUID albumId) {
        super();
        this.albumId = albumId;
    }

    /**
     * Gets the album id.
     *
     * @return the album id
     */
    public UUID getAlbumId() {
        return this.albumId;
    }

    /**
     * Sets the album id.
     *
     * @param albumId the new album id
     */
    public void setAlbumId(UUID albumId) {
        this.albumId = albumId;
    }

}
