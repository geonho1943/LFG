package com.geonho1943.LFG.controller;
import com.geonho1943.LFG.service.AppService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/appList")
    public String appList() {
        appService.fieldClear();
        appService.apiParsing();
        return "redirect:";
    }

    @PostMapping("/searchApp")
    @ResponseBody
    public List<String> searchApp(@RequestParam("name") String name){
        return appService.searchAppName(name);
    }

}
