package com.rahul.blogapplication.controllers;

import com.rahul.blogapplication.dto.PostDto;
import com.rahul.blogapplication.models.Comment;
import com.rahul.blogapplication.models.Post;
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
    @GetMapping("/update_comment/{commentId}")
    public String updatePost(@PathVariable("commentId") int commentId,Model model){
        Comment newComment = commentService.getCommentById(commentId);
        int postId=newComment.getPostId();
        model.addAttribute("postId",postId);
        model.addAttribute("newComment", newComment);
        return "ViewComments.html";
    }
    @GetMapping("/delete_comment/{commentId}")
    public String deletePost(@PathVariable("commentId") int commentId){
        Comment comment=commentService.getCommentById(commentId);
        commentService.deleteCommentByID(commentId);
        return "redirect:/view_comments/"+comment.getPostId();
    }

}
