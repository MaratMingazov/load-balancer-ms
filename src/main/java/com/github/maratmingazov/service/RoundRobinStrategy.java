package com.github.maratmingazov.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Primary
@AllArgsConstructor
public class RoundRobinStrategy implements LoadBalancingStrategy {

    private final BackendRegistry registry;
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public String chooseBackend(HttpServletRequest request) {
        var backends = registry.getBackends();
        int index = Math.abs(counter.getAndIncrement() % backends.size());
        return backends.get(index);
    }
}
