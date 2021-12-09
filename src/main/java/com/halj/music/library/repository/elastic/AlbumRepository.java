package com.halj.music.library.repository.elastic;

import java.util.UUID;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.halj.music.library.model.elastic.Album;

/**
 * The Interface AlbumRepository.
 */
@Repository
public interface AlbumRepository extends ElasticsearchRepository<Album, UUID> {

}
