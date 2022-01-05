package com.olfd.config;

import com.olfd.beans.DataSource;
import com.olfd.jpa.config.JpaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import(value = {FirstConfig.class, JpaConfig.class})
@Slf4j
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean(destroyMethod = "destroyMethod")
    @Primary
    public DataSource dataSource() {
        return new DataSource("1", "1", "1");
    }

}
