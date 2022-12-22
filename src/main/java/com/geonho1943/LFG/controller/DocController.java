package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.extraDB.Doc;
import com.geonho1943.LFG.service.DocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Doc read(@RequestParam("sub")String sub){
        Doc doc = new Doc();
        doc.setDoc_sub(sub);
        docService.read(doc);
        return doc;
    }

    @GetMapping("/docWrite")
    public String docWritePage(){
        return "doc/docWrite";
    }
    @GetMapping("/doc_post")
    @ResponseBody
    public String post(
            @RequestParam("sub")String sub, @RequestParam("writ")String writ,
            @RequestParam("cont")String cont){
        Doc doc = new Doc();
        doc.setDoc_sub(sub);
        doc.setDoc_writ(writ);
        doc.setDoc_cont(cont);
        docService.post(doc);
        return "post success!!";
    }

    @GetMapping("/doc_modify")
    @ResponseBody
    public Doc docModi(
            @RequestParam("sub")String sub,@RequestParam("cont")String cont,
            @RequestParam("idx")int idx){
        Doc doc = new Doc();
        doc.setDoc_sub(sub);
        doc.setDoc_cont(cont);
        doc.setDoc_idx(idx);
        docService.modify(doc);
        return doc;
    }

    @GetMapping("/doc_delete")
    @ResponseBody
    public String docDel(
            @RequestParam("idx")int idx){
        Doc doc = new Doc();
        doc.setDoc_idx(idx);
        docService.delete(doc);
        return doc.getDoc_idx()+" delete success!!";
    }

    @GetMapping("/doc_list")//json
    @ResponseBody
    public List<Doc> docList(Model model) {
        List<Doc> docs = docService.list();
        model.addAttribute("docs",docs);
        return docs;
    }

    @GetMapping("/doclist")
    @PostMapping("/doc_LList")//데이터 연결해서 줘야함
    public String docListPage(Model model){
        List<Doc> docs = docService.list();
        model.addAttribute("docs",docs);
        return "doc/docList";
    }
}
