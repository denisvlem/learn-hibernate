package com.denisvlem.learnhibernate.repository;

import com.denisvlem.learnhibernate.entity.Author;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author Jpa repository.
 */
public interface AuthorRepository extends JpaRepository<Author, UUID> {

  Set<Author> findAllByAuthorIdIn(Set<UUID> uuids);
}
