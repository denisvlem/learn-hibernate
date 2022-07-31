package com.denisvlem.learnhibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Book db entity.
 */
@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
  private Long bookId;

  @Column(name = "title")
  @NotBlank
  private String title;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Author author;

  @Column(name = "genre")
  @PositiveOrZero
  private Integer genre;
}
