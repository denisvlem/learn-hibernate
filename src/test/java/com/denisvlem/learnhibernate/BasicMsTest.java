package com.denisvlem.learnhibernate;

import com.denisvlem.learnhibernate.repository.AuthorRepository;
import com.denisvlem.learnhibernate.repository.BookRepository;
import com.denisvlem.learnhibernate.service.AuthorServiceJpaImpl;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Basic microservice (integration) test suite.
 * Includes rest assured rest api client configuration and transaction template
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public abstract class BasicMsTest {

  @LocalServerPort
  protected int port;

  @Autowired
  protected AuthorServiceJpaImpl authorService;

  @Autowired
  protected AuthorRepository authorRepository;

  @Autowired
  protected BookRepository bookRepository;

  @Autowired
  protected TransactionTemplate tx;

  /**
   * Set up the test rest api client.
   */
  @BeforeEach
  public void setUp() {
    Assertions.setMaxStackTraceElementsDisplayed(1000);
    RestAssured.port = port;
    RestAssured.requestSpecification = new RequestSpecBuilder()
        .setPort(port)
        .setContentType(ContentType.JSON)
        .setAccept(ContentType.JSON)
        .build();
  }

  @AfterEach
  public void cleanUp() {
    tx.executeWithoutResult(s -> bookRepository.deleteAll());
    tx.executeWithoutResult(s -> authorRepository.deleteAll());
  }
}
