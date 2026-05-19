package com.org.agent.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.agent.model.TelemetryEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TelemetryService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ElasticService elasticService;

    private final ObjectMapper mapper =
            new ObjectMapper();

    private static final String TOPIC =
            "llm-telemetry";

    public TelemetryService(
            KafkaTemplate<String, String> kafkaTemplate,
            ElasticService elasticService) {

        this.kafkaTemplate = kafkaTemplate;
        this.elasticService = elasticService;
    }

    public void publish(TelemetryEvent event) {

        try {

            /*
             * Convert to JSON
             */
            String json =
                    mapper.writeValueAsString(event);

            /*
             * Send to Kafka
             */
            kafkaTemplate.send(TOPIC, json);

            /*
             * Store in Elastic
             */
            elasticService.indexTelemetry(event);

            System.out.println(
                    "Telemetry Published: " + json
            );

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}