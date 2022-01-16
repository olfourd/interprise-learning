package com.example.carriageservice.service;

import com.example.carriageservice.domain.model.Transport;
import com.example.carriageservice.domain.repository.TransportRepository;
import com.example.carriageservice.web.error.NotFound;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Validated
public class TransportService {

    private final TransportRepository transportRepository;

    public Mono<Transport> save(@Valid Transport transport) {
        if (StringUtils.isBlank(transport.getId())) {
            return transportRepository.save(transport);
        } else {
            return transportRepository.findById(transport.getId())
                    .flatMap(tr -> {
                        tr.setGeo(transport.getGeo());
                        tr.setTransportDocument(transport.getTransportDocument());
                        tr.setTransportOwner(transport.getTransportOwner());
                        return transportRepository.save(tr);
                    })
                    .switchIfEmpty(Mono.error(
                            new NotFound(String.format("Transport not found by id: %s", transport.getId()))));
        }
    }

    public Flux<Transport> findAll() {
        return transportRepository.findAll();
    }

    public Mono<Transport> findById(@NotBlank String id) {
        return transportRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFound(String.format("Transport not found by id: %s", id))));
    }

    public Mono<Void> deleteById(@NotBlank String id) {
        return transportRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new NotFound(String.format("Transport not found by id: %s", id))))
                .flatMap(transport -> transportRepository.deleteById(transport.getId()));
    }
}