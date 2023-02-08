package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.entity.Author;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

/**
 * An author business rules service interface.
 */
@Validated
public interface AuthorService {

  Author getById(UUID id);

  Author create(String firstName, String lastName);

  Author update(Author author);

  void delete(@NotNull UUID authorId);
}
