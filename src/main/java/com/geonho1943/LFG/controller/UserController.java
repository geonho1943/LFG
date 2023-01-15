package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.User;
import com.geonho1943.LFG.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/userJoin")
    public String userJoin(User user){
        userService.join(user);
        return "/";
    }

    @PostMapping("/userLogin")
    public String userLogin(User user, HttpSession httpSession) {
        try {
            userService.login(user);
            httpSession.setAttribute("sessionLogin",user);
            System.out.println("세션 사용됨!");
            System.out.println();
        }catch (Exception e){
            return "redirect:/userError?error=ture";
        }
        return "redirect:/docList";
    }


}
