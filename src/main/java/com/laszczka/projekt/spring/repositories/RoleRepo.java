package com.laszczka.projekt.spring.repositories;

import com.laszczka.projekt.spring.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
Role findRoleByType(Role.Types type);
}
