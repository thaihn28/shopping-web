package com.example.shoppingweb.service;

import com.example.shoppingweb.dto.AdminRegistrationDTO;
import com.example.shoppingweb.model.Admin;
import com.example.shoppingweb.model.Role;
import com.example.shoppingweb.repository.AdminRepository;
import com.example.shoppingweb.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

	private AdminRepository adminRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;
	
	public AdminServiceImpl(AdminRepository adminRepository) {
		super();
		this.adminRepository = adminRepository;
	}

	@Override
	public Admin save(AdminRegistrationDTO registrationDto) {
		Admin admin = new Admin();
		admin.setFirstName(registrationDto.getFirstName());
		admin.setLastName(registrationDto.getLastName());
		admin.setUsername(registrationDto.getUsername());
		admin.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

		Role role = roleRepository.findRoleById(2L);
		admin.setRoles(Arrays.asList(role));
		
		return adminRepository.save(admin);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		Admin ad = adminRepository.findByUsername(username);
		if(ad == null) {
			throw new UsernameNotFoundException("Admin not found");
		}
		return new org.springframework.security.core.userdetails.User(ad.getUsername(), ad.getPassword(), mapRolesToAuthorities(ad.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
}
