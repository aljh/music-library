package com.halj.music.library.model.elastic;

import java.util.UUID;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * The Class Album.
 * Represents an Album in Elasticsearch
 */
@Document(indexName = "albums")
public class Album {

    /** The id. */
    @Id
    private UUID id;

    /** The title. */
    @NotBlank
    @Field(type = FieldType.Text, name = "title")
    private String title;

    /** The artist. */
    @NotBlank
    @Field(type = FieldType.Text, name = "artist")
    private String artist;

    /** The release year. */
    @Digits(fraction = 0, integer = 4)
    @Field(type = FieldType.Text, name = "releaseYear")
    private String releaseYear;

    /** The cover URL. */
    @Field(type = FieldType.Text, name = "coverURL")
    private String coverURL;

    /**
     * Instantiates a new album.
     */
    public Album() {
        super();
    }

    /**
     * Instantiates a new album.
     *
     * @param id the id
     * @param title the title
     * @param artist the artist
     * @param releaseYear the release year
     * @param coverURL the cover URL
     */
    public Album(UUID id, String title, String artist, String releaseYear, String coverURL) {
        super();
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.coverURL = coverURL;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the artist.
     *
     * @return the artist
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Sets the artist.
     *
     * @param artist the new artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gets the release year.
     *
     * @return the release year
     */
    public String getReleaseYear() {
        return this.releaseYear;
    }

    /**
     * Sets the release year.
     *
     * @param year the new release year
     */
    public void setReleaseYear(String year) {
        this.releaseYear = year;
    }

    /**
     * Gets the cover URL.
     *
     * @return the cover URL
     */
    public String getCoverURL() {
        return this.coverURL;
    }

    /**
     * Sets the cover URL.
     *
     * @param coverURL the new cover URL
     */
    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

}
