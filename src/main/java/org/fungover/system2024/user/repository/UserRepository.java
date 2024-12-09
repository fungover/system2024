package org.fungover.system2024.user.repository;

import org.fungover.system2024.user.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {
}
