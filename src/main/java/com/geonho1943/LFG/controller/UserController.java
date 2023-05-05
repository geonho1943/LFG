package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.LoginInfo;
import com.geonho1943.LFG.dto.User;
import com.geonho1943.LFG.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/userJoin")
    public String userJoin(User user){
        userService.join(user);
        LOGGER.info("User join succeeded: " +user.getUser_idx()+"/"+ user.getUser_id());
        return "redirect:/";
    }

    @PostMapping("/userLogin")
    public String userLogin(User user, HttpSession httpSession) {
        try {
            userService.login(user);
            LoginInfo loginInfo = new LoginInfo(
                    user.getUser_idx(),user.getUser_id(),
                    user.getUser_name(),user.getUser_role(),
                    user.getUser_reg()
            );
            httpSession.setAttribute("loginInfo",loginInfo );
            LOGGER.info("User login succeeded: " +user.getUser_idx()+"/"+ user.getUser_id());
        }catch (Exception e){
            return "redirect:/userError?error=ture";
        }
        return "redirect:/";
    }

    @PostMapping("/userModify")
    public String userModify(HttpSession httpSession,User user){
        userService.modify(user);
        LOGGER.info("User modify succeeded: " + user.getUser_idx());
        httpSession.removeAttribute("loginInfo");
        return "redirect:";
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(@RequestParam("id") String id) {
        return userService.check(id);
    }

}
