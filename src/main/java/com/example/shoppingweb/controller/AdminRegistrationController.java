package com.example.shoppingweb.controller;


import com.example.shoppingweb.dto.AdminRegistrationDTO;
import com.example.shoppingweb.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class AdminRegistrationController {

	private AdminService adminService;

	public AdminRegistrationController(AdminService adminService) {
		super();
		this.adminService = adminService;
	}
	
	@ModelAttribute("admin")
    public AdminRegistrationDTO userRegistrationDto() {
        return new AdminRegistrationDTO();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("admin") AdminRegistrationDTO registrationDto) {
		adminService.save(registrationDto);
		return "redirect:/registration?success";
	}
}
