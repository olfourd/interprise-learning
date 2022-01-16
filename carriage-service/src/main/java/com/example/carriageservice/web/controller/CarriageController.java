package com.example.carriageservice.web.controller;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.example.carriageservice.domain.model.Transport;
import com.example.carriageservice.service.TransportService;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class CarriageController {

    private final TransportService transportService;

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Transport> transportMono = request.bodyToMono(Transport.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(transportMono.flatMap(transportService::save), Transport.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<Transport> all = transportService.findAll();
        return defaultReadResponse(all);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        final var id = request.pathVariable("id");
        Mono<Transport> transportMono = transportService.findById(id);
        return defaultReadResponse(transportMono);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Mono<Void> transportMono = transportService.deleteById(request.pathVariable("id"));
        return defaultReadResponse(transportMono);
    }

    private Mono<ServerResponse> defaultReadResponse(Publisher<?> publisher) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(publisher, Transport.class);
    }
}
