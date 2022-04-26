package com.example.shoppingweb.repository;


import com.example.shoppingweb.model.Admin;
import com.example.shoppingweb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
	Admin findByUsername(String username);
	boolean existsByUsername(String username);
	Admin deleteByIdAndAndRole(Long id, String role);
	Admin findAdminByIdAndRole_Name(Long id, String role);
	List<Admin> findAllByRole(String role);
	List<Admin> findAllByRole_Name(String role);
 	Admin findAdminByIdAndRole(Admin admin, Role role);
}
