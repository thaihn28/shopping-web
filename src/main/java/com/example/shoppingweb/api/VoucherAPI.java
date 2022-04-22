package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Voucher;
import com.example.shoppingweb.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VoucherAPI {

    @Autowired
    VoucherService voucherService;

    @RequestMapping("/vouchers")
    public List<Voucher> viewAllVouchers(){
        return voucherService.getAllVouchers();
    }


}
