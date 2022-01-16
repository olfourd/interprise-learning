package com.example.carriageservice.web.controller;

import static com.example.carriageservice.web.controller.CarriageEndpointRouter.CARRIAGE_ENDPOINT;
import static com.example.carriageservice.web.controller.CarriageEndpointRouter.ID_PATH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.carriageservice.domain.model.Transport;
import com.example.carriageservice.domain.model.TransportDocument;
import com.example.carriageservice.domain.model.TransportOwner;
import com.example.carriageservice.domain.repository.TransportRepository;
import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarriageControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TransportRepository transportRepository;

    @Test
    void create_200() {
        final var stubTransport = stubTransport();
        stubTransport.setId(null);
        when(transportRepository.save(any(Transport.class))).thenReturn(Mono.just(stubTransport));

        webTestClient
                .post()
                .uri(CARRIAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(stubTransport).log(), Transport.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Transport.class)
                .consumeWith(response -> Objects.equals(response.getResponseBody(), stubTransport));
    }

    @Test
    void create_400() {
        final var stubTransport = stubTransport();
        stubTransport.setId(null);
        stubTransport.setTransportDocument(null);

        webTestClient.post().uri(CARRIAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(stubTransport), Transport.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void readById_200() {
        final var stubTransport = stubTransport();
        when(transportRepository.findById(stubTransport.getId())).thenReturn(Mono.just(stubTransport));

        webTestClient.get().uri(CARRIAGE_ENDPOINT + ID_PATH, stubTransport.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Transport.class)
                .consumeWith(response -> assertThat(response.getResponseBody(), is(stubTransport)));
    }

    @Test
    void readById_404() {
        when(transportRepository.findById(anyString())).thenReturn(Mono.empty());

        webTestClient.get().uri(CARRIAGE_ENDPOINT + ID_PATH, "asdadadad")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void readAll() {
        final var stubTransport = stubTransport();
        when(transportRepository.findAll()).thenReturn(Flux.just(stubTransport, stubTransport, stubTransport));

        webTestClient.get().uri(CARRIAGE_ENDPOINT)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    void update_200() {
        final var stubTransport = stubTransport();
        when(transportRepository.findById(stubTransport.getId())).thenReturn(Mono.just(stubTransport));
        when(transportRepository.save(stubTransport)).thenReturn(Mono.just(stubTransport));

        webTestClient.put().uri(CARRIAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(stubTransport), Transport.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Transport.class)
                .consumeWith(response -> Objects.equals(response.getResponseBody(), stubTransport));
    }

    @Test
    void update_400() {
        final var stubTransport = stubTransport();
        stubTransport.setTransportOwner(null);

        webTestClient.put().uri(CARRIAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(stubTransport), Transport.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void update_404() {
        final var stubTransport = stubTransport();
        when(transportRepository.findById(stubTransport.getId())).thenReturn(Mono.empty());

        webTestClient.put().uri(CARRIAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(stubTransport), Transport.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void delete_200() {
        final var stubTransport = stubTransport();
        when(transportRepository.findById(stubTransport.getId())).thenReturn(Mono.just(stubTransport));
        when(transportRepository.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.delete().uri(CARRIAGE_ENDPOINT + ID_PATH, stubTransport.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void delete_404() {
        when(transportRepository.findById(anyString())).thenReturn(Mono.empty());
        when(transportRepository.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.delete().uri(CARRIAGE_ENDPOINT + ID_PATH, "any id")
                .exchange()
                .expectStatus().isNotFound();
    }

    private Transport stubTransport() {
        return Transport
                .builder()
                .id("ID")
//                .geo(new GeoJsonPoint(1.1d, 2.2d))
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