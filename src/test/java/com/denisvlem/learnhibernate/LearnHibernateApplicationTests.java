package com.denisvlem.learnhibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

class LearnHibernateApplicationTests extends BasicMsTest {

    @Autowired
    private LearnHibernateApplication application;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();
    }

}
