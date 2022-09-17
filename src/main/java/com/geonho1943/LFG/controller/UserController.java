package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.model.UserModel;
import com.geonho1943.LFG.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    //UserModel userModel = new UserModel();
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "test_ok";
    }


    @GetMapping("/user_lookup")
    @ResponseBody
    public List<User> user_lookup(){
        return userService.findMembers();
    }
    @GetMapping("/user_test")
    @ResponseBody
    public String url_test(@RequestParam("test")String test){
        UserModel userModel = new UserModel();
        return userModel.test();
    }


    @GetMapping("/user_join")
    public String user_join(@RequestParam("name")String name, @RequestParam("pw")String pw){
        UserModel userModel = new UserModel();
        return userModel.save(name,pw);
    }

}
