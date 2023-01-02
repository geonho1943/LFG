package com.geonho1943.LFG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/userlogin")
    public String loginPage(){
        return "user/userLogin";
    }

    @GetMapping("/userjoin")
    public String joinPage(){
        return "user/userJoin";
    }

    @GetMapping("/docWrite")
    public String docWritePage(){
        return "doc/docWrite";
    }
}
