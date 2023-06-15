package com.rahul.blogapplication.controllers;

import com.rahul.blogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import  com.rahul.blogapplication.models.User;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;

   @PostMapping("/addUser")
    public void add(){
       User user=new User();
       user.setName("Vignesh");
       user.setPassword("1");
       user.setRole("ROLE_AUTHOR");
       System.out.println(userService.addUser(user));

       User user2=new User();
       user2.setName("Teja");
       user2.setPassword("1");
       user2.setRole("ROLE_AUTHOR");
       System.out.println(userService.addUser(user2));

    }
}
