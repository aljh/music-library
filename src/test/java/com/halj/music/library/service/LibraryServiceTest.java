package com.halj.music.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.halj.music.library.model.User;
import com.halj.music.library.model.elastic.Album;
import com.halj.music.library.repository.elastic.AlbumRepository;

/**
 * 
 * @author halj
 *
 */
@ExtendWith(SpringExtension.class)
class LibraryServiceTest {

    @TestConfiguration
    static class LibraryServiceTestContextConfiguration {

        @Bean
        @Primary
        public LibraryService playerService(UserService userService, AlbumRepository albumRepository) {
            return new LibraryService(userService, albumRepository);
        }

    }

    // Mock repositories - we are no testing that repositories work
    @MockBean
    UserService userService;

    @MockBean
    AlbumRepository albumRepository;

    // target class to be tested
    @Autowired
    LibraryService libraryService;

    private static long USER_ID_1 = 1l;
    private static UUID ALBUM_ID_1 = UUID.fromString("c26e1aa2-b52c-4cb0-a907-15a2e323960d");
    private static UUID ALBUM_ID_2 = UUID.fromString("b9930c12-5e79-4cd3-8630-363222983a4c");

    @BeforeEach
    public void setUp() {
        User userJohn = new User(USER_ID_1, "john", "john@email.com");

        Mockito.when(this.userService.getUser(USER_ID_1))
                .thenReturn(userJohn);

        // mock album 1 in user library
        {
            // reset mocked data
            List<Album> mockedLibrary = new ArrayList<>();
            Set<UUID> mockedAlbumIds = new HashSet<>();
            mockedLibrary.add(new Album(ALBUM_ID_1, "Green Day in America", "Green Day", "1990", null));
            mockedAlbumIds.add(ALBUM_ID_1);
            Mockito.when(this.albumRepository.findAllById(mockedAlbumIds)).thenReturn(mockedLibrary);
        }

        // mock album 2 in user library
        {
            // reset mocked data
            List<Album> mockedLibrary = new ArrayList<>();
            Set<UUID> mockedAlbumIds = new HashSet<>();
            mockedLibrary.add(new Album(ALBUM_ID_2, "Fluffy Vs. Phantasmic", "Fluffy", "1996", null));
            mockedAlbumIds.add(ALBUM_ID_2);
            Mockito.when(this.albumRepository.findAllById(mockedAlbumIds)).thenReturn(mockedLibrary);
        }

        // mock albums 1 and 2 in user library
        {
            // reset mocked data
            List<Album> mockedLibrary = new ArrayList<>();
            Set<UUID> mockedAlbumIds = new HashSet<>();
            mockedLibrary.add(new Album(ALBUM_ID_1, "Green Day in America", "Green Day", "1990", null));
            mockedLibrary.add(new Album(ALBUM_ID_2, "Fluffy Vs. Phantasmic", "Fluffy", "1996", null));
            mockedAlbumIds.add(ALBUM_ID_1);
            mockedAlbumIds.add(ALBUM_ID_2);
            Mockito.when(this.albumRepository.findAllById(mockedAlbumIds)).thenReturn(mockedLibrary);
        }

    }

    @Test
    void test_addSingleAlbumAndGetLibrary() {

        // user library should be empty before adding albums
        List<Album> library = toList(this.libraryService.getAlbums(USER_ID_1));
        assertEquals(0, library.size());

        // add 1 album
        this.libraryService.addAlbum(ALBUM_ID_1, USER_ID_1);
        library = toList(this.libraryService.getAlbums(USER_ID_1));

        assertEquals(1, library.size());
        assertEquals(ALBUM_ID_1, library.get(0).getId());
        assertEquals("Green Day in America", library.get(0).getTitle());
    }

    @Test
    void test_addAlbumsAndGetLibrary() {
        List<UUID> albumIds = new ArrayList<>();
        albumIds.add(ALBUM_ID_1);
        albumIds.add(ALBUM_ID_2);

        // library should be empty before adding albums
        List<Album> library = toList(this.libraryService.getAlbums(USER_ID_1));
        assertEquals(0, library.size());

        // add 1 album
        this.libraryService.addAlbums(albumIds, USER_ID_1);
        library = toList(this.libraryService.getAlbums(USER_ID_1));

        assertEquals(2, library.size());
        assertEquals(ALBUM_ID_1, library.get(0).getId());
        assertEquals("Green Day in America", library.get(0).getTitle());
        assertEquals(ALBUM_ID_2, library.get(1).getId());
        assertEquals("Fluffy Vs. Phantasmic", library.get(1).getTitle());
    }

    @Test
    void test_removeSingleAlbumAndGetLibrary() {

        // put 2 albums in library
        this.libraryService.addAlbum(ALBUM_ID_1, USER_ID_1);
        this.libraryService.addAlbum(ALBUM_ID_2, USER_ID_1);

        List<Album> library = toList(this.libraryService.getAlbums(USER_ID_1));
        assertEquals(2, library.size());

        // remove first album from library
        this.libraryService.removeAlbum(ALBUM_ID_1, USER_ID_1);

        library = toList(this.libraryService.getAlbums(USER_ID_1));
        assertEquals(1, library.size());
        assertEquals(ALBUM_ID_2, library.get(0).getId());
        assertEquals("Fluffy Vs. Phantasmic", library.get(0).getTitle());
    }

    @Test
    void test_removeAlbumsAndGetLibrary() {

        List<UUID> libraryItems = new ArrayList<>();
        libraryItems.add(ALBUM_ID_1);
        libraryItems.add(ALBUM_ID_2);

        // put 2 albums in library
        this.libraryService.addAlbums(libraryItems, USER_ID_1);

        List<Album> library = toList(this.libraryService.getAlbums(USER_ID_1));
        assertEquals(2, library.size());

        // remove 2 albums from library
        this.libraryService.removeAlbums(libraryItems, USER_ID_1);

        library = toList(this.libraryService.getAlbums(USER_ID_1));
        assertEquals(0, library.size());

    }

    private static List<Album> toList(Iterable<Album> iterable) {
        List<Album> albums = new ArrayList<>();
        iterable.forEach(albums::add);
        return albums;
    }

}
