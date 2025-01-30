package com.oauth2.server.web;

import com.oauth2.server.model.UserInfo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Controller {
    @GetMapping("/signIn")
    public String signIn (){
        return "signIn";
    }

    @PostMapping("/login_process")
    public String login_process (){
        System.out.println("loginProcess");
        return "signIn1";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String createUser(@ModelAttribute UserInfo userInfo, Model model) {
        System.out.println("first Name : {}" + userInfo);
        model.addAttribute("userInfo", new UserInfo());
        return "signIn1";
    }

}
