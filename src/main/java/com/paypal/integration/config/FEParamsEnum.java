package com.paypal.integration.config;

import lombok.Getter;
import lombok.Setter;

public enum FEParamsEnum {

    CALLBACK_SUCCESS_LINK(1L, "http://localhost:4200/success"),
    CALLBACK_FAILURE_LINK(2L, "http://localhost:4200/error"),
    CALLBACK_CANCEL_LINK(3L, "http://localhost:4200");

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String value;

    FEParamsEnum(Long id, String value) {
        this.id = id;
        this.value = value;
    }
}
