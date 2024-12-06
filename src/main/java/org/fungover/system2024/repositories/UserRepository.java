package org.fungover.system2024.repositories;

import org.fungover.system2024.entities.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {
}
