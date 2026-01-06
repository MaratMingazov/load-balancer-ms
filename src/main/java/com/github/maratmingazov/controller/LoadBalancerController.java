package com.github.maratmingazov.controller;

import com.github.maratmingazov.proxy.ProxyClient;
import com.github.maratmingazov.service.LoadBalancingStrategy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class LoadBalancerController {

    private final LoadBalancingStrategy strategy;
    private final ProxyClient proxyClient;

    @RequestMapping("/**")
    public ResponseEntity<String> proxy(HttpServletRequest request) throws IOException {
        var backend = strategy.chooseBackend(request);
        var response = proxyClient.forward(backend, request);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }
}
