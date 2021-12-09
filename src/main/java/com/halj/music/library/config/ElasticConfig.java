package com.halj.music.library.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * The Class ElasticConfig.
 * Configuration for Elasticsearch client
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.halj.model.elastic.repository")
public class ElasticConfig {

    /** The elastic host. */
    @Value("${elasticsearch.host:localhost}")
    private String host;

    /** The elastic port. */
    @Value("${elasticsearch.port:9200}")
    private int port;

    @Value("${elasticsearch.auth.enable:false}")
    private boolean authentication;

    @Value("${elasticsearch.auth.username:username}")
    private String username;

    @Value("${elasticsearch.auth.password:password}")
    private String password;

    /**
     * Client.
     *
     * @return the rest high level client
     */
    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = this.authentication ? authenticationClientConfiguration() : simpleClientConfiguration();

        return RestClients.create(clientConfiguration).rest();
    }

    private ClientConfiguration simpleClientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(this.host + ":" + this.port)
                .build();
    }

    private ClientConfiguration authenticationClientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(this.host + ":" + this.port)
                .usingSsl()
                .withBasicAuth(this.username, this.password)
                .build();
    }

    /**
     * Elasticsearch template.
     *
     * @return the elasticsearch operations
     */
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
