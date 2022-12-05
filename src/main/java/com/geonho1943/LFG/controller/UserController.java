package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.service.UserService;
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
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user_join")
    @ResponseBody
    public String user_join(
            @RequestParam("id")String id,@RequestParam("pw")String pw,
            @RequestParam("name")String name){
        User user = new User();
        user.setUser_id(id);
        user.setUser_pw(pw);
        user.setUser_name(name);
        userService.join(user);
        return " idx : " + user.getUser_idx() + " id : " + user.getUser_id() + " name : " + user.getUser_name() + " join success!!";

    }
    @GetMapping("/userjoin")
    public String joinTemp(){
        return "user/userJoin";
    }

    @PostMapping("/user_join")
    public String user_join(userForm form){
        User user = new User();
        user.setUser_id(form.getId());
        user.setUser_pw(form.getPw());
        user.setUser_name(form.getName());
        userService.join(user);
        return "/home";
    }

    @GetMapping("/user_list")
    @ResponseBody
    public List<User> user_list(Model model){
        List<User> users = userService.lookup();
        model.addAttribute("users",users);
        return users;
    }

    @GetMapping("/user_login")
    @ResponseBody
    public User user_pick(
            @RequestParam("id")String id,@RequestParam("pw")String pw){
        User user = new User();
        user.setUser_id(id);
        user.setUser_pw(pw);
        userService.pick(user);
        return user;
       //return "idx : "+user.getUser_idx()+" id : " + user.getUser_id() + " name : " + user.getUser_name()+" login success!!";
    }
    @PostMapping("/user_Login")
    public String user_page_login(
            @RequestParam("id")String id, @RequestParam("pw")String pw, HttpSession httpSession) {
        User user = new User();
        user.setUser_id(id);
        user.setUser_pw(pw);
        userService.pick(user);
        System.out.println("doc 리스트 조회 할 타이밍");
        return "doc/docList";
    }

    @GetMapping("/userlogin")
    public String loginTemp(){
        return "user/userLogin";
    }


    @GetMapping("/user_modify")
    @ResponseBody
    public User user_modify(
            @RequestParam("idx")int idx,@RequestParam("id")String id,
            @RequestParam("pw")String pw,@RequestParam("name")String name){
        User user = new User();
        user.setUser_idx(idx);
        user.setUser_id(id);
        user.setUser_pw(pw);
        user.setUser_name(name);
        userService.modify(user);
        return user;
    }

    @GetMapping("/user_sleep")
    @ResponseBody
    public User user_sleep(
            @RequestParam("idx")int idx){
        User user = new User();
        user.setUser_idx(idx);
        userService.sleep(user);
            return user;
    }

    @GetMapping("/loginform")
    public String loginform(){
        return "loginform";
    }
}
