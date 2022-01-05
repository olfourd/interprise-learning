package com.olfd.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Optional;

@EnableAutoConfiguration
@EnableJpaAuditing(auditorAwareRef = "customAuditorAware")
@EnableJpaRepositories(
        basePackages = "com.olfd.domain.repository",
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
)
@EntityScan("com.olfd.domain.model")
@EnableEnversRepositories
@EnableTransactionManagement
public class JpaConfig {

    private static final String SYS_USER_STUB = "stub system user name";

    @Bean
    public AuditorAware<String> customAuditorAware() {
        return new CustomAuditorAware();
    }

    private class CustomAuditorAware implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.of(SYS_USER_STUB);
        }
    }
}
