package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.extraDB.Doc;
import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.service.DocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DocController {

    private final DocService docService;

    public DocController(DocService docService) {
        this.docService = docService;
    }

    @GetMapping("/doc_read")
    @ResponseBody
    public Doc read(@RequestParam("tit")String tit){
        Doc doc = new Doc();
        doc.setDoc_tit(tit);
        docService.read(doc);
        return doc;
    }
    @GetMapping("/doc_post")
    @ResponseBody
    public String post(
            @RequestParam("tit")String tit, @RequestParam("writ")String writ,
            @RequestParam("cont")String cont){
        Doc doc = new Doc();
        doc.setDoc_tit(tit);
        doc.setDoc_writ(writ);
        doc.setDoc_cont(cont);
        docService.post(doc);
        return "post success!!";
    }

    @GetMapping("/doc_list")
    @ResponseBody
    public List<Doc> doc_list(Model model) {
        List<Doc> docs = docService.list();
        model.addAttribute("docs",docs);
        return docs;
    }

    @GetMapping("/doc_modify")
    @ResponseBody
    public Doc doc_modi(
            @RequestParam("tit")String tit,@RequestParam("cont")String cont,
            @RequestParam("idx")int idx){
        Doc doc = new Doc();
        doc.setDoc_tit(tit);
        doc.setDoc_cont(cont);
        doc.setDoc_idx(idx);
        docService.modify(doc);
        return doc;
    }

    @GetMapping("/doc_delete")
    @ResponseBody
    public String doc_del(
            @RequestParam("idx")int idx){
        Doc doc = new Doc();
        doc.setDoc_idx(idx);
        docService.delete(doc);
        return doc.getDoc_idx()+" delete success!!";
    }

}
