package com.github.maratmingazov.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class BackendRegistry {

    private final List<String> backends = List.of(
            "http://localhost:8090",
            "http://localhost:8095"
    );

}
