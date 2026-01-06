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
        String backend = strategy.chooseBackend(request);

        byte[] body = null;
        if (request.getContentLength() > 0) {body = request.getInputStream().readAllBytes();}

        var response = proxyClient.forward(backend, request, body);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }
}
