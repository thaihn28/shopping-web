package com.example.shoppingweb.controller;

import com.example.shoppingweb.model.Discount;
import com.example.shoppingweb.model.Voucher;
import com.example.shoppingweb.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class DiscountController {
    @Autowired
    DiscountService discountService;

    @GetMapping("/discounts")
    public String viewAllDiscounts(Model model){
        model.addAttribute("discounts", discountService.getAllDiscounts());
        return "/discount/discounts";
    }

    @GetMapping("/discounts/add")
    public String addDiscount(Model model){
        model.addAttribute("discount" , new Discount());
        model.addAttribute("pageTitle" , "Add new discount");
        return "/discount/discountAdd";
    }

    @PostMapping("/discounts/add")
    public String saveDiscount(@RequestParam(value = "discountId", required = false) Long discountId, @ModelAttribute("discount") Discount discount, RedirectAttributes ra ){
        discountService.addDiscount(discount);
        if(discountId == null){
            ra.addFlashAttribute("msg", "Added successfully");
        }else {
            ra.addFlashAttribute("msg", "Updated successfully");
        }
        return "redirect:/admin/discounts";
    }

    @GetMapping("/discounts/delete/{id}")
    public String deleteDiscount(@PathVariable(value = "id") Long id, RedirectAttributes ra){
        discountService.deleteDiscountByID(id);
        ra.addFlashAttribute("msg", "Deleted successfully");
        return "redirect:/admin/discounts";
    }
}
