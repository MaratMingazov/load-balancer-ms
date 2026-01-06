package com.github.maratmingazov.service;

import jakarta.servlet.http.HttpServletRequest;

public interface LoadBalancingStrategy {

    String chooseBackend(HttpServletRequest request);
}
