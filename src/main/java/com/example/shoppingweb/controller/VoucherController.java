package com.example.shoppingweb.controller;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.model.Voucher;
import com.example.shoppingweb.service.CategoryService;
import com.example.shoppingweb.service.ProductService;
import com.example.shoppingweb.service.UserNotFoundException;
import com.example.shoppingweb.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class VoucherController {
    @Autowired
    VoucherService voucherService;

    @GetMapping("/vouchers")
    public String viewAllVouchers(Model model){
        model.addAttribute("vouchers", voucherService.getAllVouchers());
        return "/voucher/vouchers";
    }

    @GetMapping("/vouchers/add")
    public String addVoucher(Model model){
        model.addAttribute("voucher" , new Voucher());
        model.addAttribute("pageTitle" , "Add new voucher");
        return "/voucher/voucherAdd";
    }

    @PostMapping("/vouchers/add")
    public String saveVoucher(@RequestParam(value = "voucherId", required = false) Long voucherId, @ModelAttribute("voucher") Voucher voucher, RedirectAttributes ra ){
        voucherService.addVoucher(voucher);
        if(voucherId == null){
            ra.addFlashAttribute("msg", "Added successfully");
        }else {
            ra.addFlashAttribute("msg", "Updated successfully");
        }
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/vouchers/delete/{id}")
    public String deleteVoucherById(@PathVariable(value = "id") Long id, RedirectAttributes ra){
        voucherService.deleteVoucherByID(id);
        ra.addFlashAttribute("msg", "Deleted successfully");
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/vouchers/update/{id}")
    public String updateVoucherById(@PathVariable(value = "id") Long id, Model model){
        try {
            Voucher voucher = voucherService.getVoucherByID(id);
            model.addAttribute("voucher", voucher);
            model.addAttribute("pageTitle" , "Update voucher (Id: " + id + ")" );
            return "/voucher/voucherAdd";
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "redirect:/admin/vouchers";
        }
    }

    @GetMapping("/vouchers/detail/{id}")
    public String detailCategory(@PathVariable("id") Long id, Model model){
        try {
            Voucher voucher = voucherService.getVoucherByID(id);
            model.addAttribute("voucher", voucher);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "redirect:/admin/vouchers";
        }
        return "/voucher/voucherDetail";
    }
}
