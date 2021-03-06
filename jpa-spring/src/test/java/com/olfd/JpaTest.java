package com.olfd;

import com.olfd.config.JpaConfig;
import com.olfd.domain.model.Position;
import com.olfd.domain.repository.PositionRepository;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.history.Revisions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaConfig.class})
class JpaTest {

    @Autowired
    PositionRepository positionRepository;

    @SpyBean
    AuditorAware<String> auditorAware;

    @Test
    @SneakyThrows
    void autoSettingRevisionFields() {
        var stubPosition = stubPosition();

        final var sys_user = "sys_user";
        when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of(sys_user));
        final var position = positionRepository.save(stubPosition);

        assertThat(position.getCreatedBy(), is(sys_user));
        assertThat(position.getCreatedDate(), notNullValue());
        assertThat(position.getLastModifiedBy(), is(sys_user));
        assertThat(position.getLastModifiedDate(), notNullValue());
        assertThat(position.getVersion(), is(0L));

        final var other_sys_user = "other sys_user";
        when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of(other_sys_user));
        await().until(() -> {
                    var updated = positionRepository.saveAndFlush(position);
                    assertThat(updated.getCreatedBy(), is(sys_user));
                    assertThat(updated.getLastModifiedBy(), is(other_sys_user));
                    assertThat(updated.getLastModifiedDate(), not(updated.getCreatedDate()));
                    assertThat(updated.getVersion(), is(1L));
                    return true;
                });
    }

    @Test
    void enversRepoMethods() {
        var position = stubPosition();
        position = positionRepository.save(position);

        position.setName("some");
        position = positionRepository.save(position);

        Revisions<Long, Position> revisions = positionRepository.findRevisions(position.getId());

        assertThat(revisions.getContent(), hasSize(2));
    }

    private Position stubPosition() {
        return Position.builder()
                .name("test position")
                .description("position for tests")
                .build();
    }
}
