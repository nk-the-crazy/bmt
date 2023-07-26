package io.gnt.bmt.identity.repository;

import io.gnt.bmt.identity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
