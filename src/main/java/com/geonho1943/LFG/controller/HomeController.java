package com.geonho1943.LFG.controller;
import org.springframework.stereotype.Controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/log-test")
    public String logTest(){
        logger.info("첫 로그 test");
        return "/home";
    }
}
