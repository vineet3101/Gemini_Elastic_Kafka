package com.org.agent.model;

public class TelemetryEvent {
	
	private String model;
    private long latencyMs;
    private int ramGb;
    private boolean success;
    private double tokensPerSec;

    public TelemetryEvent() {
    }

    public TelemetryEvent(
            String model,
            long latencyMs,
            int ramGb,
            boolean success,
            double tokensPerSec) {

        this.model = model;
        this.latencyMs = latencyMs;
        this.ramGb = ramGb;
        this.success = success;
        this.tokensPerSec = tokensPerSec;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getLatencyMs() {
        return latencyMs;
    }

    public void setLatencyMs(long latencyMs) {
        this.latencyMs = latencyMs;
    }

    public int getRamGb() {
        return ramGb;
    }

    public void setRamGb(int ramGb) {
        this.ramGb = ramGb;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getTokensPerSec() {
        return tokensPerSec;
    }

    public void setTokensPerSec(double tokensPerSec) {
        this.tokensPerSec = tokensPerSec;
    }

}
