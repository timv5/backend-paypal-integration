package com.paypal.integration.service.dao;

import com.paypal.integration.model.UserTransactions;

public interface TransactionService {

    void saveNewTransaction(UserTransactions transaction);

}
