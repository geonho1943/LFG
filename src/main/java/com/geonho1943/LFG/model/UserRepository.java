package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.User;

import java.util.List;

public interface UserRepository {
    User join(User user);

    List<User> findAll();

    User pick(User user);

    User modify(User user);

    User sleep(User user);
}
