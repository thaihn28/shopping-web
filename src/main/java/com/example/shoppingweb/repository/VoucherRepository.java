package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
boolean existsVoucherByCode(String code);
Voucher findVoucherByCode(String code);
}
