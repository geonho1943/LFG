package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.User;

import java.util.List;

public interface UserRepository {
    User join(User user);

    List<User> findAll();

    User pick(User user);

    User check(User user);

    User modify(User user);

    User sleep(User user);

    User mappingUserRole(User user);
}
