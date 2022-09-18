package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.User;

public interface UserRepository {
    User join(User user);
}
