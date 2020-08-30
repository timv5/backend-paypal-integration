package com.paypal.integration.service.impl;

import com.paypal.integration.model.Users;
import com.paypal.integration.persistance.UsersDao;
import com.paypal.integration.service.dao.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersDao usersDao;

    @Autowired
    public UsersServiceImpl(final UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public Optional<Users> getUserById(Long id) {
        Optional<Users> users = Optional.empty();
        try {
            users = this.usersDao.findById(id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return users;
    }
}
