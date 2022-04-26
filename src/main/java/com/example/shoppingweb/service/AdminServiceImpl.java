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
import java.util.Collections;
import java.util.List;
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
        admin.setRole(role);

        return adminRepository.save(admin);
    }

    @Override
    public void delete(Long id) {
        Admin staff = adminRepository.findAdminByIdAndRole_Name(id, "ROLE_STAFF");
        if (staff == null) {
            throw new UsernameNotFoundException("Could not find staff with id = " + id);
        }
        adminRepository.delete(staff);
    }


    @Override
    public Admin viewDetail(Long id) {
        Admin staff = adminRepository.findAdminByIdAndRole_Name(id, "ROLE_STAFF");
        return staff;
    }


    @Override
    public List<Admin> getAll() {
        return adminRepository.findAllByRole_Name("ROLE_STAFF");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin ad = adminRepository.findByUsername(username);
        if (ad == null) {
            throw new UsernameNotFoundException("Admin not found");
        }
        return new org.springframework.security.core.userdetails.User(ad.getUsername(), ad.getPassword(), getRoles(ad.getRole().getName()));
    }

    //	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
//		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//	}
    private Collection<? extends GrantedAuthority> getRoles(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }


}
