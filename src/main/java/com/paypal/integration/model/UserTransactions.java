package com.paypal.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "transactions")
public class UserTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "product_cost")
    private double productCost;

    @Column(name = "shipping_cost")
    private double shippingCost;

    @Column(name = "tax")
    private double tax;

    @Column(name = "currency")
    private String currency;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "user_id")
    private Long userId;

}
