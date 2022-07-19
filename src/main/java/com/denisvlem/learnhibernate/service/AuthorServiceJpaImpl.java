package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author service implementation using Jpa repository.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceJpaImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  @Override
  @Transactional(readOnly = true)
  public Author getById(long id) {
    log.debug("Author service getById() start");
    return authorRepository.getById(id);
  }

  @Override
  @Transactional
  public Author create(String firstName, String lastName) {
    log.debug("Author service create() start");

    return authorRepository.save(new Author().setFirstName(firstName).setLastName(lastName));
  }

  @Override
  @Transactional
  public Author update(Author author) {
    log.debug("Author service update() start");
    return authorRepository.save(author);
  }

  @Override
  @Transactional
  public void delete(Long authorId) {
    log.debug("Author with id = [{}] is being deleted", authorId);
    authorRepository.deleteById(authorId);
    log.debug("Author with id = [{}] is deleted", authorId);
  }
}
