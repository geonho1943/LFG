package com.geonho1943.LFG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
