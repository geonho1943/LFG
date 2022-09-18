package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "test_ok";
    }

    @GetMapping("/user_join")
    @ResponseBody
    public String join(@RequestParam("id")String id,@RequestParam("pw") String pw,@RequestParam("name")String name){
        User user = new User();
        user.setUser_id(id);
        user.setUser_pw(pw);
        user.setUser_name(name);
        userService.join(user);
        System.out.println("join이 실행중입니다 Controller. "+ user.getUser_id()+" "+user.getUser_pw()+" "+user.getUser_name());
        return " idx : " + user.getUser_idx() + "id : " + user.getUser_id() + " name : " + user.getUser_name() + " join success!!";

    }
}
