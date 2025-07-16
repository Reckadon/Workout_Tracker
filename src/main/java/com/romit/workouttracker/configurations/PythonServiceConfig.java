package com.romit.workouttracker.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PythonServiceConfig {
    private final ObjectMapper objectMapper;

    public PythonServiceConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Bean
    public WebClient pythonWebClient() {    // similarly can be used to connect to other services - can use @Qualifier to differentiate between multiple WebClients
        return WebClient.builder()
                .baseUrl("http://localhost:8000")
                // for coverting LocalDateTime to ISO-8601 format strings
                .codecs(configurer -> configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON)))
                .build();
    }
}
