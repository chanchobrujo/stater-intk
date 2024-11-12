package com.stater.intk.rest;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import static org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

@Service
@RequiredArgsConstructor
public abstract class _WebClientRest {
    @Setter
    private WebClient webClient;

    public ResponseSpec defaultGenericWebClient(String method, String URI, Object request) {
        method = method.toUpperCase();
        RequestHeadersSpec<?> httpMethod;

        switch (method) {
            case "POST": {
                httpMethod = this.webClient
                        .post()
                        .uri(URI)
                        .contentType(APPLICATION_JSON)
                        .bodyValue(request);
                break;
            }
            case "PUT": {
                httpMethod = this.webClient
                        .put()
                        .uri(URI)
                        .contentType(APPLICATION_JSON)
                        .bodyValue(request);
                break;
            }
            default: {
                httpMethod = this.webClient
                        .get()
                        .uri(URI)
                        .accept(APPLICATION_JSON);
                break;
            }
        }

        return httpMethod.retrieve();
    }
}
