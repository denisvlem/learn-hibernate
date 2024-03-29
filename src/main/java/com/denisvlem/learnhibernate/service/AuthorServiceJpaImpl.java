package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.repository.AuthorRepository;
import java.util.Set;
import java.util.UUID;
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
  public void delete(UUID authorId) {
    log.debug("Author with id = [{}] is being deleted", authorId);
    authorRepository.deleteById(authorId);
    log.debug("Author with id = [{}] has been deleted", authorId);
  }

  @Override
  @Transactional(readOnly = true)
  public Set<Author> getAuthors(Set<UUID> authorsId) {
    return authorRepository.findAllByAuthorIdIn(authorsId);
  }

}
