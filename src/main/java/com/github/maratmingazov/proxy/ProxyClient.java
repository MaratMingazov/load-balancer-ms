package com.github.maratmingazov.proxy;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;


@Log4j2
@Service
@AllArgsConstructor
public class ProxyClient {

    private final RestClient restClient;

    public ResponseEntity<String> forward(String backend, HttpServletRequest request) throws IOException {

        String targetUrl = backend + request.getRequestURI();
        if (request.getQueryString() != null) {
            targetUrl += "?" + request.getQueryString();
        }
        log.info("Request forwarding to backend {} ", targetUrl);

        var requestSpec = restClient.method(HttpMethod.valueOf(request.getMethod())).uri(targetUrl);

        if (request.getContentLength() > 0) {
            var body = request.getInputStream().readAllBytes();
            requestSpec.body(body);
        }

        var response = requestSpec.retrieve().toEntity(String.class);
        return response;
    }

}
