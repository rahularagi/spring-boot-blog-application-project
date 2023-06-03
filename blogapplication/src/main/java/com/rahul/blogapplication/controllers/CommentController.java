package com.rahul.blogapplication.controllers;

import com.rahul.blogapplication.dto.PostDto;
import com.rahul.blogapplication.models.Comment;
import com.rahul.blogapplication.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/view_comments/{postId}")
    public String viewComments(@PathVariable("postId") int postId, Model model){
        System.out.println(postId);
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        model.addAttribute("postId",postId);
        model.addAttribute("comments",comments);
        model.addAttribute("newComment",new Comment());
        System.out.println(comments);

        return "ViewComments.html";
    }
  @PostMapping("/new_comment/{postId}")
    public String saveComment(@PathVariable("postId") int postId,@ModelAttribute("newComment") Comment newComment){
        System.out.println("newComment");
        newComment.setPostId(postId);
         commentService.addPost(newComment);
        return "redirect:/view_comments/"+postId;
    }
}
