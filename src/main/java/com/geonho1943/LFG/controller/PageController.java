package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.LoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @GetMapping("/userLogin")
    public String userLoginPage(){
        return "user/userLogin";
    }

    @GetMapping("/userJoin")
    public String userJoinPage(){
        return "user/userJoin";
    }


    @GetMapping("/userError")
    public String errorPage(){
        return "user/userError";
    }

    @GetMapping("/docWrite")
    public String docWritePage(HttpSession httpSession, Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        return "doc/docWrite";
    }



    @GetMapping("/myProfile")
    public String userProfile(HttpSession httpSession, Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        return "user/userProfile";
    }
    @GetMapping("/userModify")
    public String userModify(HttpSession httpSession, Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        return "user/userModify";
    }

}
