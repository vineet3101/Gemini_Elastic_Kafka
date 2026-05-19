package com.org.agent.model;


public class RoutingDecision {

    private String model;
    private String promptStrategy;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPromptStrategy() {
        return promptStrategy;
    }

    public void setPromptStrategy(String promptStrategy) {
        this.promptStrategy = promptStrategy;
    }
}
