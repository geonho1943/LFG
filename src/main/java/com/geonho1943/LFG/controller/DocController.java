package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.Doc;
import com.geonho1943.LFG.dto.LoginInfo;
import com.geonho1943.LFG.service.AppService;
import com.geonho1943.LFG.service.DocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Controller
public class DocController {

    private final Logger logger = LoggerFactory.getLogger(DocController.class);

    private final DocService docService;
    private final AppService appService;

    public DocController(DocService docService, AppService appService) {
        this.docService = docService;
        this.appService = appService;
    }


    @GetMapping("/")
    public String docList(HttpSession httpSession,Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        List<Doc> docs = docService.list();
        model.addAttribute("loginInfo",loginInfo);
        model.addAttribute("docs",docs);
        if (loginInfo == null) logger.info("메인 페이지가 활성화되었습니다.");
        return "doc/docList";
    }

    @GetMapping("/docDetail")
    public String docDetail(@RequestParam("doc_idx")int idx,HttpSession httpSession, Model model){
        try {
            LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
            logger.info(loginInfo.getUser_idx()+" / "+loginInfo.getUser_name()+" 가 doc "+idx+" 를 조회 하였습니다");
            model.addAttribute("loginInfo",loginInfo);
        }catch (Exception e){
            return "redirect:/userError";
        }
        Doc doc = new Doc();
        doc.setDoc_idx(idx);
        docService.read(doc);
        model.addAttribute("doc",doc);
        return "doc/docDetail";
    }

    @PostMapping("/docPost")
    public String docPost(Doc doc,HttpSession httpSession,Model model
    ){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        doc.setDoc_writ(loginInfo.getUser_name());
        appService.searchAppId(doc);//회원 정보 검증
        docService.post(doc);
        logger.info(loginInfo.getUser_idx()+" / "+loginInfo.getUser_name()+" 의 새로운 글이 작성 되었습니다. ");
        return "redirect:";
    }

    @PostMapping("/docUpdate")
    public String docUpdate(Doc doc, HttpSession httpSession,Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        docService.modify(doc);
        model.addAttribute("doc",doc);
        logger.info(loginInfo.getUser_idx()+" / "+loginInfo.getUser_name()+" 가 "+doc.getDoc_idx()+" 글을 수정 했습니다.");
        return "redirect:";
    }

    @GetMapping("/docUpdate")
    public String docUpdatePage(@RequestParam("doc_idx")int doc_idx,HttpSession httpSession, Model model) {
        try {
            LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
            model.addAttribute("loginInfo", loginInfo);
            if (loginInfo == null){
                throw new NullPointerException("로그인을 하고 접속 해주세요!");
            }
        }catch (NullPointerException e){
            return "redirect:/userError?error=ture";
        }
        Doc doc = new Doc();
        doc.setDoc_idx(doc_idx);
        docService.read(doc);
        model.addAttribute("doc",doc);
        return "doc/docUpdate";
    }

    @GetMapping("/docSearch")
    public String docSearch(@RequestParam("name")String name,HttpSession httpSession,Model model){
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        Doc doc = new Doc();
        doc.setDoc_app_name(name);
        List<Doc> docs = docService.appNameList(doc);
        model.addAttribute("docs",docs);
        return "doc/docList";
    }

    @PostMapping("/docDelete")
    public String docDelete(Doc doc,HttpSession httpSession, Model model){
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        logger.info(loginInfo.getUser_idx()+" / "+loginInfo.getUser_name()+" 가 "+doc.getDoc_idx()+" 글을 삭제 했습니다.");
        docService.delete(doc);
        return "redirect:/";
    }

}
