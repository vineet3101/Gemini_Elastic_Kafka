package com.org.agent.service;

import org.springframework.stereotype.Service;
import com.org.agent.model.HardwareProfile;
import com.org.agent.model.RoutingDecision;


@Service
public class RoutingService {

    public RoutingDecision decide(HardwareProfile profile) {

        RoutingDecision decision =
                new RoutingDecision();

        /*
         * LOW-END SYSTEM
         * Less RAM available
         */
        if (profile.getRamGb() < 8) {

            decision.setModel("gemma:2b");
            decision.setPromptStrategy("SHORT");

        }

        /*
         * GPU SYSTEM
         */
        else if (profile.isGpuAvailable()
                && profile.getGpuVramGb() >= 8) {

            decision.setModel("mistral");
            decision.setPromptStrategy("DETAILED");

        }

        /*
         * CPU-ONLY MID RANGE
         */
        else {

            decision.setModel("phi3");
            decision.setPromptStrategy("BALANCED");
        }

        return decision;
    }
}
