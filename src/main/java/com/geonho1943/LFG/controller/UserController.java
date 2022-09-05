package com.geonho1943.LFG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

@GetMapping("/test")
@ResponseBody
    public String test(){
        return "test_ok";
    }

}
