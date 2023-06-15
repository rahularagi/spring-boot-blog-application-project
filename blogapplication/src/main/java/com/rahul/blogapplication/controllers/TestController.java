package com.rahul.blogapplication.controllers;


import com.rahul.blogapplication.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/login")
    public String signIn(Model model){

        return "LoginPage";
    }

}
