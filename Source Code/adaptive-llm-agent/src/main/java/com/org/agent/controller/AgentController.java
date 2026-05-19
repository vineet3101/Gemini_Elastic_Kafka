package com.org.agent.controller;


import com.org.agent.model.HardwareProfile;
import com.org.agent.model.RoutingDecision;
import com.org.agent.service.HardwareService;
import com.org.agent.service.OllamaService;
import com.org.agent.service.RoutingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final HardwareService hardwareService;
    private final RoutingService routingService;
    private final OllamaService ollamaService;

    public AgentController(
            HardwareService hardwareService,
            RoutingService routingService,
            OllamaService ollamaService) {

        this.hardwareService = hardwareService;
        this.routingService = routingService;
        this.ollamaService = ollamaService;
    }

    @PostMapping("/generate")
    public String generate(
            @RequestBody String userPrompt) {

        HardwareProfile profile =
                hardwareService.detectHardware();

        RoutingDecision decision =
                routingService.decide(profile);

        String optimizedPrompt =
                optimizePrompt(
                        userPrompt,
                        decision.getPromptStrategy()
                );

        return ollamaService.generate(
                decision.getModel(),
                optimizedPrompt
        );
    }

    private String optimizePrompt(
            String prompt,
            String strategy) {

        return switch (strategy) {

            case "SHORT" ->
                    "Answer briefly: " + prompt;

            case "DETAILED" ->
                    "Provide detailed technical analysis: "
                            + prompt;

            default ->
                    prompt;
        };
    }
}