package com.paypal.integration.controller;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.integration.config.FEParamsEnum;
import com.paypal.integration.dto.PaymentResponse;
import com.paypal.integration.dto.ExecuteRequest;
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
    public PaymentResponse authorizeUserPayment(@RequestBody UserTransactions userTransaction) {
        LOGGER.info("::: PaypalController.authorizeUserPayment :::");
        String redirectUrl = FEParamsEnum.CALLBACK_FAILURE_LINK.getValue();
        try {
            redirectUrl = this.paypalService.authorizeUserPayment(userTransaction);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new PaymentResponse(redirectUrl, "failure");
        }

        return new PaymentResponse(redirectUrl, "success");
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PostMapping("/execute")
    public PaymentResponse executePayment(@RequestBody ExecuteRequest executeRequest) {
        LOGGER.info("::: PaypalController.executePayment :::");
        try {
            Payment payment = this.paypalService.executePayment(executeRequest);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            if (payerInfo == null || transaction == null) {
                LOGGER.error("Payment not executed");
                return new PaymentResponse(FEParamsEnum.CALLBACK_FAILURE_LINK.getValue(), "failure");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new PaymentResponse(FEParamsEnum.CALLBACK_FAILURE_LINK.getValue(), "failure");
        }

        return new PaymentResponse(FEParamsEnum.CALLBACK_SUCCESS_LINK.getValue(), "success");
    }

}
