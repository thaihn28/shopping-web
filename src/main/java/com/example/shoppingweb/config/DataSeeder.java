package com.example.shoppingweb.config;


import com.example.shoppingweb.model.Admin;
import com.example.shoppingweb.model.Role;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.repository.AdminRepository;
import com.example.shoppingweb.repository.RoleRepository;
import com.example.shoppingweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		if (roleRepository.findRoleByName("ROLE_ADMIN") == null) {
			roleRepository.save(new Role("ROLE_ADMIN"));
		}

		if (roleRepository.findRoleByName("ROLE_STAFF") == null) {
			roleRepository.save(new Role("ROLE_STAFF"));
		}

		if (!adminRepository.existsByUsername("nickadmin")) {
			Admin admin = new Admin();
			admin.setUsername("nickadmin");
			Role role = roleRepository.findRoleById(1L);
			admin.setPassword(passwordEncoder.encode("123"));
			admin.setRole(role);
			admin.setFirstName("Nguyen");
			admin.setLastName("Truong");

			adminRepository.save(admin);

		}
		if (!adminRepository.existsByUsername("nickstaff")) {
			Admin admin = new Admin();
			admin.setUsername("nickstaff");
			Role role = roleRepository.findRoleById(2L);
			admin.setPassword(passwordEncoder.encode("123"));
			admin.setRole(role);
			admin.setFirstName("Nguyen");
			admin.setLastName("Van B");

			adminRepository.save(admin);

		}

		if (!userRepository.existsByUsername("client")) {
			User user = new User();
			user.setUsername("client");
			user.setPassword(passwordEncoder.encode("123"));
		    user.setEmail("client@gmail.com");
			user.setEnabled(true);
			user.setCreated(Instant.now());
			user.setFirstName("Nguyen");
			user.setLastName("Van B");
			user.setAddress("Son Tay Ha Noi");
			user.setPhoneNo("0976223223");

			userRepository.save(user);

		}
	}
}
