package com.denisvlem.learnhibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

  @ManyToOne
  @JoinColumn(name = "author_id")
  private Author author;

  @Column(name = "genre")
  @PositiveOrZero
  private Integer genre;

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
