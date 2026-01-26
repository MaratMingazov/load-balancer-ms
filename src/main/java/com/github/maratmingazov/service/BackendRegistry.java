package com.github.maratmingazov.service;

import com.github.maratmingazov.config.LoadBalancerProperties;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Service
public class BackendRegistry {

    private final List<String> backends;
    private final Set<String> unhealthy = ConcurrentHashMap.newKeySet();

    public BackendRegistry(LoadBalancerProperties properties) {
        this.backends = properties.getBackends();
    }

    public List<String> getHealthyBackends() {
        return backends.stream()
                .filter(b -> !unhealthy.contains(b))
                .toList();
    }

    public void markUnhealthy(String backend) {
        unhealthy.add(backend);
    }

    public void markHealthy(String backend) {
        unhealthy.remove(backend);
    }

}
