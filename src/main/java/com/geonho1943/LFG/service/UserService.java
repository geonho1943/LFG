package com.geonho1943.LFG.service;

import com.geonho1943.LFG.extraDB.User;
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
        System.out.println("join이 실행중입니다. "+ user.getUser_id()+" "+user.getUser_pw()+" "+user.getUser_name());
        return user;
    }
}
