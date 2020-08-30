package com.paypal.integration.service.impl;

import com.paypal.integration.model.FEParams;
import com.paypal.integration.persistance.FEParamsDao;
import com.paypal.integration.service.dao.FEParamsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FEParamsServiceImpl implements FEParamsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FEParamsServiceImpl.class);

    private final FEParamsDao feParamsDao;

    @Autowired
    public FEParamsServiceImpl(final FEParamsDao feParamsDao) {
        this.feParamsDao = feParamsDao;
    }

    @Override
    public Optional<FEParams> getFEParamById(final Long id) {
        Optional<FEParams> feParams = Optional.empty();
        try {
            feParams = this.feParamsDao.findById(id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return feParams;
    }
}
