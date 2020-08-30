package com.paypal.integration.persistance;

import com.paypal.integration.model.UserTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<UserTransactions, Long> {

}
