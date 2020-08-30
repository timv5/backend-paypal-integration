package com.paypal.integration.service.dao;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.integration.dto.ExecuteRequest;
import com.paypal.integration.model.UserTransactions;

public interface PaypalService {

    public String authorizeUserPayment(UserTransactions userTransaction) throws PayPalRESTException;

    Payment executePayment(ExecuteRequest executeRequest) throws PayPalRESTException;

}
