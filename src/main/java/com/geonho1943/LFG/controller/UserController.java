package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.dto.Doc;
import com.geonho1943.LFG.dto.LoginInfo;
import com.geonho1943.LFG.dto.User;
import com.geonho1943.LFG.service.DocService;
import com.geonho1943.LFG.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final DocService docService;

    public UserController(UserService userService, DocService docService) {
        this.userService = userService;
        this.docService = docService;
    }

    @PostMapping("/userJoin")
    public String userJoin(User user) {
        userService.join(user);
        logger.info("회원가입이 완료 되었습니다 : " + user.getUser_idx() + "/" + user.getUser_name());
        return "redirect:/";
    }

    @GetMapping("/userJoin")
    public String userJoinPage() {
        return "user/userJoin";
    }

    @PostMapping("/userLogin")
    public String userLogin(User user, HttpSession httpSession) {
        LoginInfo checkLogin = (LoginInfo) httpSession.getAttribute("loginInfo");
        if (checkLogin != null) {
            logger.info("로그인을 거부 했습니다 사유: 중복 로그인.");
            return "redirect:/userError?error=duplicate";
        }
        try {
            userService.login(user);
            LoginInfo loginInfo = new LoginInfo(
                    user.getUser_idx(), user.getUser_id(),
                    user.getUser_name(), user.getUser_role(),
                    user.getUser_reg());
            httpSession.setAttribute("loginInfo", loginInfo);
            logger.info("유저 " + user.getUser_idx() + " / " + user.getUser_name() + " 이 접속 하였습니다.");
        } catch (Exception e) {
            return "redirect:/userError?error=ture";
        }
        return "redirect:/";
    }

    @GetMapping("/userLogin")
    public String userLoginPage() {
        return "user/userLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("loginInfo");
        return "redirect:/";
    }

    @GetMapping("/myProfile")
    public String userProfile(HttpSession httpSession, Model model) {
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo", loginInfo);
        List<Doc> myDocs = docService.myDocList(loginInfo.getUser_name());
        model.addAttribute("myDocs", myDocs);
        return "user/userProfile";
    }

    @PostMapping("/userModify")
    public String userModify(HttpSession httpSession, User user) {
        userService.modify(user);
        logger.info("유저 " + user.getUser_idx() + " / " + user.getUser_name() + " 의 정보가 수정 되었습니다.");
        httpSession.removeAttribute("loginInfo");
        return "redirect:";
    }

    @GetMapping("/userModify")
    public String userModify(HttpSession httpSession, Model model){
        LoginInfo loginInfo = (LoginInfo)httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);
        return "user/userModify";
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(@RequestParam("id") String id) {
        return userService.check(id);
    }

    @GetMapping("/userError")
    public String errorPage(){
        return "user/userError";
    }

}
