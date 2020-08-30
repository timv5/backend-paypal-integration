package com.paypal.integration.persistance;

import com.paypal.integration.model.FEParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FEParamsDao extends JpaRepository<FEParams, Long> {
}
