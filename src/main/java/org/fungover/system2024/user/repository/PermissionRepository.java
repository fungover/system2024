package org.fungover.system2024.user.repository;

import org.fungover.system2024.user.entity.Permission;
import org.springframework.data.repository.ListCrudRepository;

public interface PermissionRepository extends ListCrudRepository<Permission, Integer> {
}
