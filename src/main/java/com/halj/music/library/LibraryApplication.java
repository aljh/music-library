package com.halj.music.library;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halj.music.library.model.elastic.Album;
import com.halj.music.library.repository.elastic.AlbumRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * The Class LibraryApplication.
 * Main entry point of the application
 * Loads album's global library into Elasticsearch
 */
@OpenAPIDefinition(info = @Info(
                                title = "Music Library API",
                                version = "0.1",
                                description = "Backend service with RESTful APIs that allows an administrator:\n"
                                        + "1. to curate users and albums\n"
                                        + "2. to curate users’ libraries"))
@SpringBootApplication
public class LibraryApplication {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(LibraryApplication.class);

    @Value("${library.albums.load:true}")
    private boolean doLoadAlbums;

    @Value("${library.albums.dataset:data/elastic/albums_sample.json}")
    private String datasetClasspath;

    /** The elasticsearch operations. */
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /** The album repository. */
    @Autowired
    private AlbumRepository albumRepository;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    /**
     * Delete index.
     */
    @PreDestroy
    public void deleteIndex() {
        this.elasticsearchOperations.indexOps(Album.class).delete();
    }

    /**
     * Builds the index.
     */
    @PostConstruct
    public void buildIndex() {
        this.elasticsearchOperations.indexOps(Album.class).refresh();

        if (this.doLoadAlbums) {
            this.albumRepository.deleteAll();
            this.albumRepository.saveAll(loadDataset());
        }
    }

    /**
     * Load Json dataset as a list of Album documents
     *
     * @return the list of Album documents
     */
    private Collection<Album> loadDataset() {

        // read JSON file and convert to Java Objects
        try {
            List<Album> albums = new ObjectMapper().readValue(new ClassPathResource(this.datasetClasspath).getFile(), List.class);
            return albums;
        } catch (IOException ioe) {
            LOG.error("Error while loading albums dataset", ioe);
            ioe.printStackTrace();
        }
        return Collections.emptyList();
    }

}
