package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user_join")
    public String userJoinPage(User user){
        try {
            userService.check(user);
        }catch (Exception e){
            return "/user/userErrorPage";
        }
        userService.join(user);
        return "/home";
    }

    @PostMapping("/userLogin")
    public String userLoginPage(
            @RequestParam("id")String id, @RequestParam("pw")String pw) {
            User user = new User();
            user.setUser_idx(0);
            user.setUser_id(id);
            user.setUser_pw(pw);
            userService.pick(user);
            if (user.getUser_idx()==0) return "/user/errPage";
            else {
                return "redirect:/doclist";
            }
    }


}
