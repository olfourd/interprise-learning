package com.example.carriageservice.controller;

import com.example.carriageservice.domain.model.Transport;
import com.example.carriageservice.domain.repository.TransportRepository;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/carriages")
@AllArgsConstructor
public class CarriageController {

    private static final String ID_PATH = "/{id}";

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final TransportRepository transportRepository;

    //todo: Persistent entities should not be used as arguments of "@RequestMapping" methods
    @PostMapping
    public Mono<Transport> create(@RequestBody @Valid Transport body) {
        return reactiveMongoTemplate.insert(body);
    }

    @GetMapping(ID_PATH)
    public Mono<ResponseEntity<Transport>> readById(@PathVariable String id) {
        return reactiveMongoTemplate.findById(id, Transport.class)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Transport> readAll() {
        return transportRepository.findAll();
    }

    // todo: Persistent entities should not be used as arguments of "@RequestMapping" methods
    @PutMapping(ID_PATH)
    public Mono<ResponseEntity<Transport>> update(@PathVariable String id,
                                                  @RequestBody @Valid Transport body) {
        return reactiveMongoTemplate.save(body)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(ID_PATH)
    public Mono<ResponseEntity<Transport>> delete(@PathVariable String id) {
        Query query = Query.query(Criteria.where(Transport.Fields.id).is(id));
        return reactiveMongoTemplate.findAndRemove(query, Transport.class)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
