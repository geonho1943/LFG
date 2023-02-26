package com.geonho1943.LFG.controller;
import com.geonho1943.LFG.service.AppService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/appList")
    public String appList() {
        appService.apiParsing();
        return "home";
    }
}
