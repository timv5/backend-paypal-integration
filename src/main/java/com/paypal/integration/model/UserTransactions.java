package com.paypal.integration.model;

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

    @Column(name = "shipping_cost")
    private double shippingCost;

    @Column(name = "order_cost")
    private double orderCost;

}
