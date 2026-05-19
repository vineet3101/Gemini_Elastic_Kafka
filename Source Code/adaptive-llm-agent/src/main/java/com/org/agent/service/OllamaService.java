package com.org.agent.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class OllamaService {

    private final WebClient webClient;
    
    public OllamaService(
            WebClient.Builder builder,
            @Value("${ollama.url}") String ollamaUrl) {

        this.webClient = builder.baseUrl(ollamaUrl).build();
    }

    public String generate(
            String model,
            String prompt) {

        Map<String, Object> request =
                Map.of(
                        "model", model,
                        "prompt", prompt,
                        "stream", false
                );

        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}