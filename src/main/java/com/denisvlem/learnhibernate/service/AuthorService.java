package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.entity.Author;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Service for business rules for author.
 */
public interface AuthorService {

  Author getById(long id);

  Author create(String firstName, String lastName);

  Author update(Author author);

  void delete(@Valid @NotNull Long authorId);
}
