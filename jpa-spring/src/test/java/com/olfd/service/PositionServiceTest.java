package com.olfd.service;


import com.olfd.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
class PositionServiceTest {

    @Autowired
    PositionService positionService;

    @Test
    void createAndGet() {
        positionService.createAndGet();
    }
}