package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.LoginInfo;
import com.geonho1943.LFG.dto.User;
import com.geonho1943.LFG.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        }catch (Exception e){
            return "redirect:/userError?error=ture";
        }
        return "redirect:/";
    }

    @PostMapping("/userModify")
    public String userModify(HttpSession httpSession,User user){
        userService.modify(user);
        httpSession.removeAttribute("loginInfo");
        return "redirect:";
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(@RequestParam("id") String id) {
        return userService.check(id);
    }

}
