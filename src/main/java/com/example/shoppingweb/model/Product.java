package com.example.shoppingweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    private Category category;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private int price;

    @NotNull
    @Column
    private String description;

    @NotNull
    @Column
    private int quantity;
    private String imageName;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

}
