package com.halj.music.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.halj.music.library.model.elastic.Album;
import com.halj.music.library.repository.elastic.AlbumRepository;

/**
 * The Class AlbumService.
 * Service to manage albums in global Elasticsearch library
 */
@Service
public class AlbumService {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(AlbumService.class);

    /** The Constant ALBUM_ES_INDEX. */
    private static final String ALBUM_ES_INDEX = "albums";

    /** The album repository. */
    private AlbumRepository albumRepository;

    /** The elasticsearch operations. */
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * Instantiates a new album service.
     *
     * @param albumRepository the album repository
     * @param elasticsearchOperations the elasticsearch operations
     */
    public AlbumService(AlbumRepository albumRepository, ElasticsearchOperations elasticsearchOperations) {
        super();
        this.albumRepository = albumRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * Save album.
     *
     * @param album the album
     * @return the album
     */
    public Album saveAlbum(final Album album) {
        checkUUID(album);
        return this.albumRepository.save(album);
    }

    /**
     * Save albums.
     *
     * @param albums the albums
     * @return the iterable
     */
    public Iterable<Album> saveAlbums(final List<Album> albums) {
        albums.forEach(this::checkUUID);
        return this.albumRepository.saveAll(albums);
    }

    /**
     * Delete albums.
     *
     * @param albums the albums
     */
    public void deleteAlbums(final List<UUID> albumIds) {
        this.albumRepository.deleteAllById(albumIds);
    }

    /**
     * Delete album.
     *
     * @param albumId the album id
     */
    public void deleteAlbum(final UUID albumId) {
        this.albumRepository.deleteById(albumId);
    }

    /**
     * Delete all albums.
     */
    public void deleteAllAlbums() {
        this.albumRepository.deleteAll();
    }

    /**
     * Find album.
     *
     * @param uuid the uuid
     * @return the optional
     */
    public Optional<Album> getAlbum(final UUID uuid) {
        return this.albumRepository.findById(uuid);
    }

    /**
     * Find album.
     *
     * @param uuid the uuid
     * @return the optional
     */
    public Iterable<Album> getAll() {
        return this.albumRepository.findAll();
    }

    /**
     * Free text search.
     *
     * @param query the query
     * @return the list
     */
    public List<Album> freeTextSearch(final String query) {
        LOG.info("Free text search with query: {}", query);

        // create query on multiple fields
        QueryBuilder queryBuilder = QueryBuilders
                .multiMatchQuery(query, "title", "artist", "releaseYear", "coverURL");
        // .fuzziness(Fuzziness.AUTO);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .build();

        // Execute search
        SearchHits<Album> albumHits = this.elasticsearchOperations.search(searchQuery, Album.class, IndexCoordinates.of(ALBUM_ES_INDEX));

        // Map searchHits to album list
        List<Album> albumMatches = new ArrayList<>();
        albumHits.forEach(hit -> albumMatches.add(hit.getContent()));

        return albumMatches;
    }

    /**
     * Check UUID.
     *
     * @param album the album
     */
    private void checkUUID(final Album album) {
        if (album.getId() == null) {
            album.setId(UUID.randomUUID());
        }
    }

}
