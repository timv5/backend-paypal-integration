package com.paypal.integration.service.dao;

import com.paypal.integration.model.FEParams;

import java.util.Optional;

public interface FEParamsService {

    Optional<FEParams> getFEParamById(final Long id);

}
