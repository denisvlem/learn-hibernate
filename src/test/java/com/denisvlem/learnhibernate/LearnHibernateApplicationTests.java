package com.denisvlem.learnhibernate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LearnHibernateApplicationTests extends BasicMsTest {

  @Autowired
  private LearnHibernateApplication application;

  @Test
  void contextLoads() {
    assertThat(application).isNotNull();
  }

  @Test
  void mainTest() {
    // todo figure out why throws an exception
    assertThatThrownBy(() ->
        LearnHibernateApplication.main(new String[]{})).isInstanceOf(Exception.class);
  }

}
