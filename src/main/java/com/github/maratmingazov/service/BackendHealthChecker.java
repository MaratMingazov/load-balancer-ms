package com.github.maratmingazov.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Log4j2
@Service
@AllArgsConstructor
public class BackendHealthChecker {

    private final RestClient restClient;
    private final BackendRegistry registry;

    private boolean isHealthy(String backend) {
        try {
            restClient.get()
                    .uri(backend + "/health")
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void checkHealth() {
        registry.getBackends().forEach(backend -> {
            if (isHealthy(backend)) {
                log.info("Backend {} is healthy", backend);
                registry.markHealthy(backend);
            } else {
                log.info("Backend {} is not healthy", backend);
                registry.markUnhealthy(backend);
            }
        });
    }
}
