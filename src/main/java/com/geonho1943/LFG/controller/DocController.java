package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.Doc;
import com.geonho1943.LFG.service.DocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DocController {

    private final DocService docService;

    public DocController(DocService docService) {
        this.docService = docService;
    }

    @PostMapping("/docPost")
    public String docPost(
            @RequestParam("sub")String sub, @RequestParam("writ")String writ,
            @RequestParam("cont")String cont){
        Doc doc = new Doc();
        doc.setDoc_sub(sub);
        doc.setDoc_writ(writ);
        doc.setDoc_cont(cont);
        docService.post(doc);
        return "redirect:/docList";
    }

    @GetMapping("/docList")
    public String docList(Model model){
        List<Doc> docs = docService.list();
        model.addAttribute("docs",docs);
        return "doc/docList";
    }
}
