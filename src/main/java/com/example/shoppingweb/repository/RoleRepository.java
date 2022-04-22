package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
    Role findRoleByName(String role);
}
