package com.org.agent.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

    @Value("${elastic.url}")
    private String elasticUrl;

    @Bean
    public ElasticsearchClient elasticsearchClient() {

        RestClient restClient =
                RestClient.builder(
                        HttpHost.create(elasticUrl)
                ).build();

        RestClientTransport transport =
                new RestClientTransport(
                        restClient,
                        new JacksonJsonpMapper()
                );

        return new ElasticsearchClient(transport);
    }
}
