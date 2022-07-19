package com.denisvlem.learnhibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Add book rest request body.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddBookRequestDto {

  private String title;
  private int genre;
  private Long authorId;
}
