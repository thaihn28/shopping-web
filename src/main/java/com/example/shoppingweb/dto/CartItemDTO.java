package com.example.shoppingweb.dto;

import com.example.shoppingweb.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Product product;
    private int quantity;
}
