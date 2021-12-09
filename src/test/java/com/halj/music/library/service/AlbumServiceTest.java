package com.halj.music.library.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.halj.music.library.model.elastic.Album;
import com.halj.music.library.repository.elastic.AlbumRepository;

@ExtendWith(SpringExtension.class)
class AlbumServiceTest {

    @TestConfiguration
    static class AlbumServiceTestContextConfiguration {

        @Bean
        @Primary
        public AlbumService albumService(AlbumRepository albumRepository, ElasticsearchOperations elasticsearchOperations) {
            return new AlbumService(albumRepository, elasticsearchOperations);
        }

    }

    // Mock Elasticsearch repository
    @MockBean
    AlbumRepository albumRepository;

    @MockBean
    ElasticsearchOperations elasticsearchOperations;

    // target class to be tested
    @Autowired
    AlbumService albumService;

    @Test
    void test_uuidGeneratedIfMissingWhenSave() {

        Album album = new Album(null, "Green Day in America", "Green Day", "1990", "http://fakecover.com/cover.jpg");

        Mockito.when(this.albumRepository.save(album))
                .thenReturn(album);

        assertNull(album.getId());

        Album saved = this.albumService.saveAlbum(album);

        assertNotNull(saved.getId());
    }

    @Test
    void test_uuidGeneratedOnCollectionIfMissingWhenSave() {

        Album album_1 = new Album(null, "Green Day in America", "Green Day", "1990", "http://fakecover.com/cover.jpg");
        Album album_2 = new Album(null, "Fluffy Vs. Phantasmic", "Fluffy", "1996", "http://anotherfakecover.com/cover.jpg");

        List<Album> albums = new ArrayList<>();
        albums.add(album_1);
        albums.add(album_2);

        Mockito.when(this.albumRepository.saveAll(albums))
                .thenReturn(albums);

        albums.forEach(album -> assertNull(album.getId()));

        Iterable<Album> saved = this.albumService.saveAlbums(albums);

        saved.forEach(album -> assertNotNull(album.getId()));
    }

}
