package com.example.shoppingweb.controller;

import com.example.shoppingweb.model.*;
import com.example.shoppingweb.repository.AdminRepository;
import com.example.shoppingweb.repository.OrderRepository;
import com.example.shoppingweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AdminRepository adminRepository;

    private Admin getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findByUsername(auth.getName());

        return admin;
    }

    @GetMapping("/view-orders")
    public String viewAllOrder(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order/order-list";
    }

    @GetMapping("/view-order-detail/{id}")
    public String viewOrderDetail(@PathVariable("id") Long id, Model model) {
        Order order = orderRepository.findById(id).get();
        model.addAttribute("order", order);

        return "order/order-detail";
    }

    @GetMapping("/reject-order/{id}")
    public String rejectOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Order order = orderRepository.findById(id).get();
        if (order.getStatus().equalsIgnoreCase("Approved") ||
                order.getStatus().equalsIgnoreCase("Confirmed")) {
            redirectAttributes.addFlashAttribute("errmsg", "Cannot reject confirmed or approved order!!");

            return "redirect:/order/view-order-detail/" + order.getId();
        }
        order.setStatus("Rejected");
        order.setRejectTime(new Date());
        redirectAttributes.addFlashAttribute("msg", "Order " + id + " status has been rejected");

        Order saveOrder = orderRepository.save(order);

        return "redirect:/order/view-order-detail/" + order.getId();
    }

    @GetMapping("/approve-order/{id}")
    public String approveOrder(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Order order = orderRepository.findById(id).get();
        if (order.getStatus().equalsIgnoreCase("Rejected") ||
                order.getStatus().equalsIgnoreCase("Confirmed")) {
            redirectAttributes.addFlashAttribute("errmsg", "Cannot approve confirmed or rejected order!!");

            return "redirect:/order/view-order-detail/" + order.getId();
        }
        order.setStatus("Approved");
        order.setApproveTime(new Date());
        redirectAttributes.addFlashAttribute("msg", "Order " + id + " status has been set to approved");

        Order saveOrder = orderRepository.save(order);

        return "redirect:/order/view-order-detail/" + order.getId();
    }


    @GetMapping("/confirm-order/{id}")
    public String confirmOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Order order = orderRepository.findById(id).get();
        if (order.getStatus().equalsIgnoreCase("Rejected")) {
            redirectAttributes.addFlashAttribute("errmsg", "Cannot confirm rejected order");

            return "redirect:/order/view-order-detail/" + id;
        }

        if (order.getStatus().equalsIgnoreCase("Waiting")) {
            redirectAttributes.addFlashAttribute("errmsg", "Order must be accepted first");

            return "redirect:/order/view-order-detail/" + id;
        }

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Product product = orderDetail.getProduct();
            product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
            productRepository.save(product);
        }
        order.setStatus("Confirmed");
        order.setConfirmTime(new Date());
        orderRepository.save(order);

        redirectAttributes.addFlashAttribute("msg", "Order " + id + " status has been set to confirm");
        return "redirect:/order/view-order-detail/" + id;
    }

}
