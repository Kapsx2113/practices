package com.sigua.practices.controller;

import com.sigua.practices.model.User;
import com.sigua.practices.services.UserSrevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

    @Controller
    @RequiredArgsConstructor
    public class UserContoller {
      private final UserSrevice userSrevice;
    @GetMapping("/login")
    public String login(){
    return "login";
}
    @GetMapping("/registration")
    public String registration() {
    return "registration";
    }
    @PostMapping("/registration")
    public String createUser(User user){
        userSrevice.createUsers(user);
        return "redirect:/login";
    }
        @GetMapping("/hello")
        public String urlSecure(){
            return "/hello";
        }
}
