package com.example.carriageservice.domain.repository;

import com.example.carriageservice.container.MongoTestContainer;
import com.example.carriageservice.domain.model.Transport;
import com.example.carriageservice.domain.model.TransportDocument;
import com.example.carriageservice.domain.model.TransportOwner;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class TransportRepositoryTest extends MongoTestContainer {

    @Autowired
    private TransportRepository transportRepository;

    @Test
    void create() {
        var detachedEntity = stubTransport();
        Mono<Transport> transportMono = transportRepository.save(detachedEntity);

        StepVerifier
                .create(transportMono)
                .expectNextMatches(transport -> StringUtils.isNotBlank(transport.getId()))
                .verifyComplete();
    }

    @Test
    void readById() {
        Transport stub = stubTransport();
        Mono<Transport> founded = transportRepository.save(stub)
                .flatMap(transport -> transportRepository.findById(transport.getId()));

        StepVerifier
                .create(founded)
                .expectNextMatches(transport ->
                        ObjectUtils.allNotNull(
                                transport.getId(),
                                transport.getTransportOwner())
                                &&
                                ObjectUtils.equals(transport.getGeo(), stub.getGeo())
                                &&
                                ObjectUtils.equals(transport.getTransportDocument(), stub.getTransportDocument()))
                .verifyComplete();
    }

    @Test
    void readAll() {
        Flux<Transport> savedTransports =
                transportRepository.saveAll(List.of(stubTransport(), stubTransport(), stubTransport()));
        Flux<Transport> composite = transportRepository.findAll().thenMany(savedTransports);

        Predicate<Transport> match = transport -> transport.getId() != null;
        StepVerifier
                .create(composite)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }

    @Test
    void update() {
        final var geoToUpdate = new GeoJsonPoint(3.3, 4.5);

        Mono<Transport> updated = transportRepository.save(stubTransport())
                .flatMap(transport -> {
                    transport.setGeo(geoToUpdate);
                    return transportRepository.save(transport);
                });

        StepVerifier
                .create(updated)
                .expectNextMatches(transport -> Objects.equals(transport.getGeo(), geoToUpdate))
                .verifyComplete();
    }

    @Test
    void delete() {
        var stub = stubTransport();
        Mono<Transport> deleted = transportRepository
                .save(stub)
                .flatMap(transport -> transportRepository.deleteById(transport.getId()).thenReturn(transport));

        StepVerifier
                .create(deleted)
                .expectNextMatches(transport -> Objects.equals(stub.getGeo(), transport.getGeo()))
                .verifyComplete();
    }

    private Transport stubTransport() {
        return Transport
                .builder()
                .geo(new GeoJsonPoint(1.1, 2.2))
                .transportOwner(
                        TransportOwner.builder()
                                .firstName("first name")
                                .middleName("middle name")
                                .lastName("last name")
                                .country("Belarus")
                                .build())
                .transportDocument(
                        TransportDocument.builder()
                                .number("any document number")
                                .expiredDate(LocalDate.now().plusYears(2))
                                .build()
                )
                .build();
    }
}