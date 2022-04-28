package com.example.shoppingweb.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long voucherId;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String code;

    @NotNull
    @Column
    private int price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
}
