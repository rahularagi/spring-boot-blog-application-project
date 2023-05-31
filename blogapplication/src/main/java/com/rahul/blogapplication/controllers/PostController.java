package com.rahul.blogapplication.controllers;

import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping("/new_post")
    public String newPost(Model model){
        model.addAttribute("post",new Post());
        return "post.html";
    }
    @PostMapping("/savepost")
    public String savePost(@ModelAttribute("post") Post post){
        postService.addPost(post);
        return "redirect:/";
    }
}
