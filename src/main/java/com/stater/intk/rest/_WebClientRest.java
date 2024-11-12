package com.stater.intk.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;
import static org.springframework.web.reactive.function.client.WebClient.*;

@Service
@RequiredArgsConstructor
public abstract class _WebClientRest {
    private final WebClient webClient = builder()
            .build();

    private RequestBodySpec getMethod(String method, String URI) {
        method = method.toUpperCase();
        RequestBodyUriSpec _method = this.webClient.post();
        switch (method) {
            case "POST": {
                _method = this.webClient.post();
                break;
            }
            case "PUT": {
                _method = this.webClient.put();
                break;
            }
            case "PATCH": {
                _method = this.webClient.patch();
                break;
            }
        }
        return _method.uri(URI)
                .contentType(APPLICATION_JSON);
    }

    private RequestHeadersSpec<?> setToken(RequestHeadersSpec<?> _methodBody, String token) {
        if (nonNull(token)) {
            _methodBody = _methodBody
                    .header(AUTHORIZATION, token);
        }
        return _methodBody;
    }

    public ResponseSpec defaultGenericWebClientGet(String URI, String token) {
        RequestHeadersSpec<?> getMethod = this.webClient
                .get()
                .uri(URI);
        return this.setToken(getMethod, token)
                .accept(APPLICATION_JSON)
                .retrieve();
    }

    public ResponseSpec defaultGenericWebClient(String method, String URI, Object request, String token) {
        RequestHeadersSpec<?> _methodBody = this.getMethod(method, URI)
                .bodyValue(request);
        return this.setToken(_methodBody, token)
                .accept(APPLICATION_JSON)
                .retrieve();
    }

    public ResponseSpec defaultGenericWebClient(String method, String URI, MultiValueMap<String, String> request, String token) {
        RequestHeadersSpec<?> _methodBody = this.getMethod(method, URI)
                .body(fromFormData(request));
        return this.setToken(_methodBody, token)
                .accept(APPLICATION_FORM_URLENCODED)
                .retrieve();
    }
}
