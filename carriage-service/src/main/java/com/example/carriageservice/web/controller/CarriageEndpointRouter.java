package com.example.carriageservice.web.controller;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CarriageEndpointRouter {

    public static final String CARRIAGE_ENDPOINT = "/carriages";
    public static final String ID_PATH = "/{id}";

    @Bean
    RouterFunction<ServerResponse> routes(CarriageController handler) {
        return RouterFunctions
                .route(POST(CARRIAGE_ENDPOINT)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(GET(CARRIAGE_ENDPOINT)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(CARRIAGE_ENDPOINT + ID_PATH)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(PUT(CARRIAGE_ENDPOINT)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(DELETE(CARRIAGE_ENDPOINT + ID_PATH)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::delete);
    }
}
