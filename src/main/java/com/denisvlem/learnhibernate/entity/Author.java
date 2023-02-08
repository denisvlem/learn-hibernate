package com.denisvlem.learnhibernate.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@ToString
@RequiredArgsConstructor
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

  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Book> books;

  @Version
  private int version;

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
