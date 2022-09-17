package com.geonho1943.LFG.service;

import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.model.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    회원 가입
    public String join(User user){
        //이름 중복 회원 x
        validateDuplicateMember(user);//중복 회원 검증
        userRepository.save(user);
        return user.getUser_id();
    }

    private void validateDuplicateMember(User user) {
        userRepository.findByName(user.getUser_name())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원 입니다.");
                });
    }
    //전체 회원 조회
    public List<User> findMembers(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long memberId){
        return userRepository.findById(memberId);
    }



}
