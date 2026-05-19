package com.org.agent.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;

import com.org.agent.model.TelemetryEvent;
import org.springframework.stereotype.Service;

@Service
public class ElasticService {

    private final ElasticsearchClient elasticsearchClient;

    public ElasticService(
            ElasticsearchClient elasticsearchClient) {

        this.elasticsearchClient =
                elasticsearchClient;
    }

    /*
     * Store telemetry
     */
    public void indexTelemetry(
            TelemetryEvent event) {

        try {

            IndexResponse response =
                    elasticsearchClient.index(i -> i
                            .index("llm-telemetry")
                            .document(event)
                    );

            System.out.println(
                    "Indexed document id: "
                            + response.id()
            );

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    /*
     * Search telemetry by model
     */
    public SearchResponse<TelemetryEvent>
    searchByModel(String model) {

        try {

            return elasticsearchClient.search(s -> s
                            .index("llm-telemetry")
                            .query(q -> q
                                    .match(
                                            MatchQuery.of(m -> m
                                                    .field("model")
                                                    .query(model)
                                            )
                                    )
                            ),
                    TelemetryEvent.class
            );

        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }
}