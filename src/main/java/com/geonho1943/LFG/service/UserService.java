package com.geonho1943.LFG.service;

import com.geonho1943.LFG.dto.User;
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
//    UserController의 check를 여기서 할까..
//        try {
//            userService.check(user);
//        }catch (Exception e){
//            return "/user/userErrorPage";
//        }
        userRepository.join(user);
        userRepository.role(user);
        return user;
    }

    public List<User> lookup() {
        return userRepository.findAll();
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
    public User check(User user) {
        return userRepository.check(user);
    }

}
