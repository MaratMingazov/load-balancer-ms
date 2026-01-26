package com.github.maratmingazov.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IpHashStrategy implements LoadBalancingStrategy{

    private final BackendRegistry registry;

    @Override
    public String chooseBackend(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        var backends = registry.getBackends();
        if (backends.isEmpty()) {throw new RuntimeException("No available backends");}
        int index = Math.abs(ip.hashCode() % backends.size());
        return backends.get(index);
    }
}
