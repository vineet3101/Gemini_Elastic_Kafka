package com.org.agent.model;

public class HardwareProfile {

    private int ramGb;
    private int cpuCores;
    private boolean gpuAvailable;
    private int gpuVramGb;

    public int getRamGb() {
        return ramGb;
    }

    public void setRamGb(int ramGb) {
        this.ramGb = ramGb;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public boolean isGpuAvailable() {
        return gpuAvailable;
    }

    public void setGpuAvailable(boolean gpuAvailable) {
        this.gpuAvailable = gpuAvailable;
    }

    public int getGpuVramGb() {
        return gpuVramGb;
    }

    public void setGpuVramGb(int gpuVramGb) {
        this.gpuVramGb = gpuVramGb;
    }
}
