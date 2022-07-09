package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.BasicMsTest;
import com.denisvlem.learnhibernate.entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class AuthorServiceTest extends BasicMsTest {


    @Test
    void testOptimisticLock_SwitchVersion() {
        //given
        var denisVer0 = authorService.create("Denis", "Emelyanov");
        assertThat(denisVer0.getVersion()).isZero();

        //when
        var denisVer1 = authorService.update(denisVer0.setFirstName("Andrey"));

        //then
        assertThat(denisVer1.getVersion()).isOne();
    }

    @Test
    void givenRepositoryRequest_whenNothingChanged_DontSwitchVersion() {
        //given
        var denisVer0 = authorRepository.save(new Author().setFirstName("Denis").setLastName("Emelyanov"));
        assertThat(denisVer0.getVersion()).isZero();

        //when
        var denisVer1 = authorRepository.save(denisVer0);

        //then
        assertThat(denisVer1.getVersion()).isZero();
    }

    @Test
    void givenRepeatableRequest_whenDone_shouldThrowOptimisticLockException() {
        //given
        var denisVer0 = authorService.create("Denis", "Emelyanov");
        assertThat(denisVer0.getVersion()).isZero();

        log.info("Version 0 is created");

        //when
        authorService.update(denisVer0.setFirstName("Andrew"));
        log.info("Version 0 is updated to 1");

        //then
        assertThatThrownBy(
                () -> tx.executeWithoutResult(
                        s -> authorService.update(denisVer0.setFirstName("Andrew")
                        ))
        ).isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }
}
