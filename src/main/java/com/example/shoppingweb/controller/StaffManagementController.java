package com.example.shoppingweb.controller;

import com.example.shoppingweb.model.Admin;
import com.example.shoppingweb.repository.AdminRepository;
import com.example.shoppingweb.repository.RoleRepository;
import com.example.shoppingweb.service.AdminServiceImpl;
import com.example.shoppingweb.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/staff-management")
public class StaffManagementController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/delete-staff/{id}")
    public String deleteStaff(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.delete(id);
            redirectAttributes.addFlashAttribute("msg", "Delete staff with id = " + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errmsg", "Can't delete staff with id = " + id);
        }
        return "redirect:/staff-management/view-all-staffs";
    }

    @RequestMapping("/view-all-staffs")
    public String viewAllStaff(Model model) {
        List<Admin> staffs = adminService.getAll();
        model.addAttribute("staffs", staffs);

        return "staff-management/all-staff";
    }


    @RequestMapping("/edit-staff/{id}")
    public String editStaff(@PathVariable("id") Long id, Model model,
                            RedirectAttributes redirectAttributes) {
        Admin staff = adminService.viewDetail(id);
        if (staff == null) {
            redirectAttributes.addFlashAttribute("errmsg", "Can't find staff with id = " + id);
            return "redirect:/staff-management/view-all-staffs";
        } else {
            model.addAttribute("staff", staff);
            return "staff-management/edit-staff";
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateStaff(@ModelAttribute("staff") Admin staff,
                              RedirectAttributes redirectAttributes) {

        staff.setRole(roleRepository.findRoleById(2L));
        adminRepository.save(staff);
        redirectAttributes.addFlashAttribute("msg", "Update staff successfully");
        return "redirect:/staff-management/edit-staff/" + staff.getId();
    }

}
