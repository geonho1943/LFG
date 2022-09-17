package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> findAll();
    String test();
    String save(String name, String pw);
}
