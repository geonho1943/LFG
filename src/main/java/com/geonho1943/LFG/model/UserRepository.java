package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.User;

public interface UserRepository {
    User join(User user);

    User login(User user);

    User auth(User user);

    boolean check(User user);

    User modify(User user);

    User sleep(User user);

    User role(User user);
}
