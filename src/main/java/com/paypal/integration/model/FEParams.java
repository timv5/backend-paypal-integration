package com.paypal.integration.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "fe_params")
public class FEParams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;


    @Column(name = "description")
    private String description;


    @Column(name = "fe_value")
    private String value;
}
