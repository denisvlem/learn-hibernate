package com.denisvlem.learnhibernate;

import com.denisvlem.learnhibernate.repository.AuthorRepository;
import com.denisvlem.learnhibernate.repository.BookRepository;
import com.denisvlem.learnhibernate.repository.GenreRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Базовый класс с конфигурацией для тестов базы данных.
 */
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional(propagation = Propagation.NEVER)
public abstract class BasicJpaTest {

  @Autowired
  protected AuthorRepository authorRepository;

  @Autowired
  protected BookRepository bookRepository;

  @Autowired
  protected GenreRepository genreRepository;

  @Autowired
  protected TransactionTemplate tx;

  /**
   * Очистить базу данных.
   */
  @AfterEach
  public final void cleanUp() {
    tx.executeWithoutResult(s -> {
      bookRepository.deleteAll();
      authorRepository.deleteAll();
      genreRepository.deleteAll();
    });
  }
}
