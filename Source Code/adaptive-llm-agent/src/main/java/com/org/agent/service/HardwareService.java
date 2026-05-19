package com.org.agent.service;
import com.org.agent.model.HardwareProfile;
import org.springframework.stereotype.Service;

@Service
public class HardwareService {

    public HardwareProfile detectHardware() {

        HardwareProfile profile = new HardwareProfile();

        long ram =
                Runtime.getRuntime().maxMemory() /
                (1024 * 1024 * 1024);

        profile.setRamGb((int) ram);

        profile.setCpuCores(
                Runtime.getRuntime().availableProcessors()
        );

        // Simplified GPU detection
        profile.setGpuAvailable(false);
        profile.setGpuVramGb(0);

        return profile;
    }
}