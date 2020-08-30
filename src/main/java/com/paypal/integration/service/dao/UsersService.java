package com.paypal.integration.service.dao;

import com.paypal.integration.model.Users;

import java.util.Optional;

public interface UsersService {

    Optional<Users> getUserById(final Long id);

}
