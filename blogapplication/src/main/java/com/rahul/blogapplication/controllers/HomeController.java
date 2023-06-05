package com.rahul.blogapplication.controllers;

import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home( Model model) {
        return findPaginated(1,"title","asc",model);
    }
    @GetMapping("/{pageNo}")
    public String findPaginated(@PathVariable(value="pageNo") int pageNo,@RequestParam("sortField") String sortField,@RequestParam("sortDir") String sortDir,Model model) {
        int pageSize = 10;
        Page<Post> page=postService.getPaginatedPost(pageNo, pageSize,sortField,sortDir);
        List<Post> listPosts=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("listPosts",listPosts);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir);
        return "Home.html";
    }
}
