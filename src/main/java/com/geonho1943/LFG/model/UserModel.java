package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.User;

import java.util.List;
import java.util.Optional;

public class UserModel implements UserRepository {
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public String test(){
        return test();
    }

    @Override
    public String save(String name, String pw) {
        return null;
    }

}
