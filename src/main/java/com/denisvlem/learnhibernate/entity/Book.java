package com.denisvlem.learnhibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
  private String title;

  @ManyToOne
  @JoinColumn(name = "author_id")
  @EqualsAndHashCode.Exclude
  private Author author;

  @Column(name = "genre")
  private Integer genre;
}
