package com.github.maratmingazov.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Service
public class BackendRegistry {

    private final List<String> backends = List.of(
            "http://localhost:8090",
            "http://localhost:8095"
    );

    private final Set<String> unhealthy = ConcurrentHashMap.newKeySet();

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
