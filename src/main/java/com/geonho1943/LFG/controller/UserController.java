package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.LoginInfo;
import com.geonho1943.LFG.dto.User;
import com.geonho1943.LFG.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/userJoin")
    public String userJoin(User user){
        userService.join(user);
        LOGGER.info("회원가입이 완료 되었습니다 : " +user.getUser_idx()+"/"+ user.getUser_name());
        return "redirect:/";
    }

    @PostMapping("/userLogin")
    public String userLogin(User user, HttpSession httpSession) {
        LoginInfo checkLogin = (LoginInfo) httpSession.getAttribute("loginInfo");
        if (checkLogin != null) {
            LOGGER.info("로그인을 거부 했습니다 사유: 중복 로그인.");
            return "redirect:/userError?error=duplicate";
        }
        try {
            userService.login(user);
            LoginInfo loginInfo = new LoginInfo(
                    user.getUser_idx(),user.getUser_id(),
                    user.getUser_name(),user.getUser_role(),
                    user.getUser_reg()
            );
            httpSession.setAttribute("loginInfo",loginInfo );
            LOGGER.info("유저 "+user.getUser_idx()+" / "+user.getUser_name()+" 이 접속 하였습니다.");
        }catch (Exception e){
            return "redirect:/userError?error=ture";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("loginInfo");
        LOGGER.info("로그아웃 하였습니다");
        return "redirect:/";
    }


    @PostMapping("/userModify")
    public String userModify(HttpSession httpSession,User user){
        userService.modify(user);
        LOGGER.info("유저 "+user.getUser_idx()+" / "+user.getUser_name()+" 의 정보가 수정 되었습니다.");
        httpSession.removeAttribute("loginInfo");
        return "redirect:";
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(@RequestParam("id") String id) {
        return userService.check(id);
    }

}
