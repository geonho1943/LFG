package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.User;
import com.geonho1943.LFG.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/userJoin")
    public String userJoin(User user){
        userService.join(user);
        return "/home";
    }

    @PostMapping("/userLogin")
    public String userLogin(User user) {
            userService.login(user);
            if (user.getUser_idx()==0) return "/user/errPage";
            else {return "redirect:/docList";}
    }


}
