package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.Doc;
import com.geonho1943.LFG.dto.LoginInfo;
import com.geonho1943.LFG.service.DocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DocController {

    private final DocService docService;

    public DocController(DocService docService) {
        this.docService = docService;
    }

    @PostMapping("/docPost")
    public String docPost(
            @RequestParam("sub")String sub,
            @RequestParam("cont")String cont,HttpSession httpSession,Model model
    ){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        Doc doc = new Doc();
        doc.setDoc_sub(sub);
        //doc.setDoc_writ(writ);
        doc.setDoc_writ(loginInfo.getUser_name());
        doc.setDoc_cont(cont);
        docService.post(doc);
        return "redirect:/docList";
    }

    @GetMapping("/docList")
    public String docList(HttpSession httpSession,Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        List<Doc> docs = docService.list();
        model.addAttribute("loginInfo",loginInfo);
        model.addAttribute("docs",docs);
        return "doc/docList";
    }

    @GetMapping("/docDetail")
    public String docDetail(@RequestParam("doc_idx")int idx,HttpSession httpSession, Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        Doc doc = new Doc();
        doc.setDoc_idx(idx);
        docService.read(doc);
        model.addAttribute("doc",doc);
        return "doc/docDetail";
    }
    @PostMapping("/docUpdate")
    public String docUpdate(Doc doc,HttpSession httpSession, Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        docService.modify(doc);
        model.addAttribute("doc",doc);
        return "redirect:/docList";
    }



    }
