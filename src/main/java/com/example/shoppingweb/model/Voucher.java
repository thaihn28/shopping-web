package com.example.shoppingweb.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
}
