package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.extraDB.Doc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DocController {

    @GetMapping("/doctest")
    @ResponseBody
    public String Test(){
        return "ok";
    }

    @GetMapping("/docread")
    @ResponseBody
    public Doc read(){
        Doc doc = new Doc();

        return doc;
    }


}
