package com.denisvlem.learnhibernate.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
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

  @NotNull
  private String title;

  private String description;

  @NotEmpty
  private Set<UUID> authorIds;

  @NotEmpty
  private Set<UUID> genreIds;
}
