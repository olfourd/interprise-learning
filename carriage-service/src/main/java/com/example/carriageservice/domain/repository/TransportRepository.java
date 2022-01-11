package com.example.carriageservice.domain.repository;

import com.example.carriageservice.domain.model.Transport;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends ReactiveMongoRepository<Transport, String> {

}
