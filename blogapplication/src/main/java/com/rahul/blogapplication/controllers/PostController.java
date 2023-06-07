package com.rahul.blogapplication.controllers;

import com.rahul.blogapplication.dto.PostDto;
import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.models.Tag;
import com.rahul.blogapplication.services.PostService;
import com.rahul.blogapplication.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @GetMapping("/new_post")
    public String newPost(Model model){
        model.addAttribute("postDto",new PostDto());
        return "Post.html";
    }
    @PostMapping("/save_post")
    public String savePost(@ModelAttribute("postDto") PostDto postDto){
        postService.addPost(postDto);
        return "redirect:/";
    }
    @GetMapping("/view_post/{postId}")
    public String viewPost(@PathVariable("postId") int postId, Model model) {
        // Retrieve the post details based on the postId
        Post post = postService.getPostById(postId);

        // Add the post details to the model
        model.addAttribute("post", post);
        return "PostDetails.html";
    }
   @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable("postId") int postId){
        postService.deletePostByID(postId);
        return "redirect:/";
    }
    @GetMapping("/update/{postId}")
    public String updatePost(@PathVariable("postId") int postId,Model model){
        Post post = postService.getPostById(postId);
        PostDto postDto= postService.getPostDto(post);
        model.addAttribute("postDto", postDto);
        return "Post.html";
    }
}
