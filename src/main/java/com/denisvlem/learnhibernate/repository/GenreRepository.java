package com.denisvlem.learnhibernate.repository;

import com.denisvlem.learnhibernate.entity.Genre;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Genre repository.
 */
public interface GenreRepository extends JpaRepository<Genre, UUID> {
  Set<Genre> findAllByIdIn(Set<UUID> uuids);
}
