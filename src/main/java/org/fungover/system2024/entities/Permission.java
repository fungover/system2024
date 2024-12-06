package org.fungover.system2024.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Size(max = 255)
  @NotNull
  private String name;

  @Size(max = 255)
  @NotNull
  private String description;

  @ManyToMany(mappedBy = "permissions")
  private Set<Role> roles = new LinkedHashSet<>();
}
