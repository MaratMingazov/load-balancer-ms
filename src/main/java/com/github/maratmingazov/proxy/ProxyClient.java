package com.github.maratmingazov.proxy;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.URI;


@Log4j2
@Service
@AllArgsConstructor
public class ProxyClient {

    private final RestClient restClient;

    public ResponseEntity<String> forward(String backend, HttpServletRequest request) throws IOException {

        StringBuilder targetUrl = new StringBuilder(backend + request.getRequestURI());
        if (request.getQueryString() != null) {
            targetUrl.append("?").append(request.getQueryString());
        }
        log.info("Request forwarding to backend {} ", targetUrl);

        // Create URI from already-encoded string to prevent double encoding
        URI uri = URI.create(targetUrl.toString());
        var requestSpec = restClient.method(HttpMethod.valueOf(request.getMethod())).uri(uri);

        // Read body for methods that can have request body
        // getContentLength() returns -1 when Content-Length header is not set (e.g., chunked encoding)
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        if (method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH) {
            var body = request.getInputStream().readAllBytes();
            if (body.length > 0) {
                requestSpec.body(body);
            }
        }

        return requestSpec.retrieve().toEntity(String.class);
    }

}
