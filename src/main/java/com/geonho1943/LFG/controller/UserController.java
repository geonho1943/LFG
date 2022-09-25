package com.geonho1943.LFG.controller;

import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "test_ok";
    }

    @GetMapping("/user_join")
    @ResponseBody
    public String user_join(@RequestParam("id")String id,@RequestParam("pw") String pw,@RequestParam("name")String name){
        User user = new User();
        user.setUser_id(id);
        user.setUser_pw(pw);
        user.setUser_name(name);
        userService.join(user);
        return " idx : " + user.getUser_idx() + " id : " + user.getUser_id() + " name : " + user.getUser_name() + " join success!!";
    }

    @GetMapping("/user_list")
    @ResponseBody
    public List<User> user_list(Model model){
        List<User> users = userService.lookup();
        model.addAttribute("users",users);
        return users;
    }

    @GetMapping("/user_pick")
    @ResponseBody
    public String user_pick(@RequestParam("id")String id){
        User user = new User();
        user.setUser_id(id);
        userService.pick(user);
        return " idx : " + user.getUser_idx() + " id : " + user.getUser_id() + " name : " + user.getUser_name();
    }

    @GetMapping("/user_modify")
    @ResponseBody
    public String user_modify(
            @RequestParam("idx")int idx,@RequestParam("id")String id,
            @RequestParam("pw")String pw,@RequestParam("name")String name){
        User user = new User();
        user.setUser_idx(idx);
        user.setUser_id(id);
        user.setUser_pw(pw);
        user.setUser_name(name);
        userService.modify(user);
        return user.getUser_idx()+" modify success!!";
    }

    @GetMapping("/user_sleep")
    @ResponseBody
    public String user_sleep(
            @RequestParam("idx")int idx){
        User user = new User();
        user.setUser_idx(idx);
        userService.sleep(user);
            return user.getUser_idx()+" delete success!!";
    }
}
