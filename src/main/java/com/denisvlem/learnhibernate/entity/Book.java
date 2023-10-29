package com.denisvlem.learnhibernate.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

/**
 * Book db entity.
 */
@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "book_id")
  private UUID bookId;

  @Column(name = "title")
  @NotBlank
  private String title;

  @Column(name = "description")
  private String description;

  @ManyToMany(
      mappedBy = "books",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE}
  )
  @Exclude
  private Set<Author> authors = new HashSet<>();


  @ManyToMany(
      mappedBy = "books",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE}
  )
  @Exclude
  private Set<Genre> genres = new HashSet<>();


  public void addAuthors(Set<Author> authors) {
    this.authors.addAll(authors);
    authors.forEach(author -> author.addBook(this));
  }

  public void addGenres(Set<Genre> genres) {
    this.genres.addAll(genres);
    genres.forEach(genre -> genre.addBook(this));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Book book = (Book) o;
    return bookId != null && Objects.equals(bookId, book.bookId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
