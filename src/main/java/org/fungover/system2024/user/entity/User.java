package org.fungover.system2024.user.entity;

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
@Table(name = "user", schema = "system24db")
public class User extends BaseEntity {

    @Size(max = 255)
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Size(max = 255)
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Size (max = 100)
    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size (max = 60)
    @NotNull
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(
    name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
        name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new LinkedHashSet<>();
}
