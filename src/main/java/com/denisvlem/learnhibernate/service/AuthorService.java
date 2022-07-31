package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.entity.Author;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Service for business rules for author.
 */
@Validated
public interface AuthorService {

  Author getById(long id);

  Author create(String firstName, String lastName);

  Author update(Author author);

  void delete(@NotNull Long authorId);
}
