package com.org.agent.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiService(WebClient.Builder builder) {

        this.webClient = builder
                .baseUrl(
                    "https://generativelanguage.googleapis.com"
                )
                .build();
    }

    /*
     * Ask Gemini
     */
    public String askGemini(String prompt) {

        try {

            Map<String, Object> textPart =
                    Map.of(
                            "text",
                            prompt
                    );

            Map<String, Object> part =
                    Map.of(
                            "parts",
                            List.of(textPart)
                    );

            Map<String, Object> request =
                    Map.of(
                            "contents",
                            List.of(part)
                    );

            String response = webClient.post()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path("/v1beta/models/gemini-2.0-flash:generateContent")
                                    .queryParam("key", apiKey)
                                    .build()
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;

        } catch (Exception ex) {

            ex.printStackTrace();

            return "Gemini API Error";
        }
    }

    /*
     * Intelligent routing decision
     */
    public String decideModelStrategy(
            String hardwareInfo,
            String userPrompt) {

        String plannerPrompt = """
            You are an AI orchestration planner.

            Based on hardware:
            %s

            And user request:
            %s

            Choose:
            1. Best LLM
            2. Prompt complexity
            3. Whether GPU is required

            Respond concisely.
            """.formatted(
                hardwareInfo,
                userPrompt
        );

        return askGemini(plannerPrompt);
    }
}