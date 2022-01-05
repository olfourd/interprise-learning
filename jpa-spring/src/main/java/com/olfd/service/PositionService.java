package com.olfd.service;

import com.olfd.domain.model.Position;
import com.olfd.domain.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    private PositionService positionService;

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    // call proxied method
    @Transactional
    public Position createAndGet() {
        Position position = positionService.create();
        return positionService.getById(position.getId());
    }

    @Transactional
    public Position create() {
        Position position = Position.builder()
                .name("some")
                .description("desc")
                .build();
        return positionRepository.saveAndFlush(position);
    }

    @Transactional(readOnly = true)
    public Position getById(@NonNull Long id) {
        return positionRepository.getById(id);
    }
}
