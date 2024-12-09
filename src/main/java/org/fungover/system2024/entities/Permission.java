package org.fungover.system2024.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

  @Size(max = 255)
  @NotNull
  private String name;

  @Size(max = 255)
  @NotNull
  private String description;

  @ManyToMany(mappedBy = "permissions")
  private Set<Role> roles = new LinkedHashSet<>();
}
