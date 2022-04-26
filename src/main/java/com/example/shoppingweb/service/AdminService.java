package com.example.shoppingweb.service;


import com.example.shoppingweb.dto.AdminRegistrationDTO;
import com.example.shoppingweb.model.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AdminService extends UserDetailsService{
	Admin save(AdminRegistrationDTO registrationDto);
	void delete(Long id);
	Admin viewDetail(Long id);
	List<Admin> getAll();
}
