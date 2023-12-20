package com.goorm.BITA.domain.file;

import com.goorm.BITA.domain.file.dto.CompileRequest;
import com.goorm.BITA.domain.file.dto.CompileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CompileService {
    private final WebClient webClient;

    public CompileService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.codex.jaagrav.in").build();
    }

    public CompileResponse compileFile(CompileRequest requestDto) {
        String code = requestDto.getCode();
        String language = requestDto.getLanguage();
        String input = requestDto.getInput();

        CompileResponse response = webClient.post()
                .uri("/")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("code", code)
                        .with("language", language)
                        .with("input", input))
                .retrieve()
                .bodyToMono(CompileResponse.class)
                .block();

        return response;
    }
}
