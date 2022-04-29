package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Voucher;
import com.example.shoppingweb.service.UserNotFoundException;
import com.example.shoppingweb.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VoucherAPI {

    @Autowired
    VoucherService voucherService;

    @RequestMapping("/vouchers")
    public List<Voucher> viewAllVouchers(){
        return voucherService.getAllVouchers();
    }

    @RequestMapping("/voucher/{id}")
    public Voucher getVoucher(@PathVariable(value = "id") Long id) {
        try {
            return voucherService.getVoucherByID(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
