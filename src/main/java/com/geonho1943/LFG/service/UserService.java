package com.geonho1943.LFG.service;

import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
@Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user){
        userRepository.join(user);
        return user;
    }

    public List<User> lookup() {
        return userRepository.findAll();
    }


    public User pick(User user) {
        System.out.println("user pick 서비스 실행");
        return userRepository.pick(user);
    }

    public User modify(User user) {
        return userRepository.modify(user);
    }

    public User sleep(User user) {
        return userRepository.sleep(user);
    }
}
