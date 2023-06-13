package com.denisvlem.learnhibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

/**
 * Author db entity.
 */
@Entity
@Table(name = "author")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "author_id")
  private UUID authorId;

  @Column(name = "first_name")
  @NotBlank
  private String firstName;

  @Column(name = "last_name")
  @NotBlank
  private String lastName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "description")
  private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "author_book",
      joinColumns = @JoinColumn(name = "author_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id"))
  private Set<Book> books;

  @Version
  private int version;

  public void addBook(Book book) {
    this.books.add(book);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Author author = (Author) o;
    return authorId != null && Objects.equals(authorId, author.authorId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
