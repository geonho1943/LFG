package com.geonho1943.LFG.service;

import com.geonho1943.LFG.dto.User;
import com.geonho1943.LFG.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        userRepository.role(user);
        return user;
    }

    public User login(User user) {
        userRepository.login(user);
        userRepository.auth(user);
        return user;

    }

    public User modify(User user) {
        return userRepository.modify(user);
    }

    public User sleep(User user) {
        return userRepository.sleep(user);
    }
    public boolean check(String id) {
        User user = new User();
        user.setUser_id(id);
        return userRepository.check(user);

    }

}
