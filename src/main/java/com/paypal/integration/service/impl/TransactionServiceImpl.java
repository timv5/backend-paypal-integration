package com.paypal.integration.service.impl;

import com.paypal.integration.model.UserTransactions;
import com.paypal.integration.persistance.TransactionDao;
import com.paypal.integration.service.dao.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.paypal.api.payments.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionDao transactionDao;

    @Autowired
    public TransactionServiceImpl(final TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public void saveNewTransaction(UserTransactions transaction) {
        try {
            this.transactionDao.save(transaction);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
