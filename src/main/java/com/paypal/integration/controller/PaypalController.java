package com.paypal.integration.controller;

import com.paypal.integration.config.FEParamsEnum;
import com.paypal.integration.dto.AuthorizePaymentResponse;
import com.paypal.integration.model.UserTransactions;
import com.paypal.integration.service.impl.PaypalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestMapping(value = "/api/paypal")
@RestController
public class PaypalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaypalController.class);

    private final PaypalServiceImpl paypalService;

    @Autowired
    public PaypalController(final PaypalServiceImpl paypalService) {
        this.paypalService = paypalService;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PostMapping("/authorize")
    public AuthorizePaymentResponse authorizeUserPayment(@RequestBody UserTransactions userTransaction) {
        LOGGER.info("::: PaypalController.authorizeUserPayment :::");
        String redirectUrl = FEParamsEnum.CALLBACK_SUCCESS_LINK.getValue();
        try {
            redirectUrl = this.paypalService.authorizeUserPayment(userTransaction);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return new AuthorizePaymentResponse(redirectUrl);
    }

}
