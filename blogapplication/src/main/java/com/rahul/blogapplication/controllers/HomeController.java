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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;
/*
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

 */
    //Page<Post> page = postService.searchAndPaginatePost(keyword,pageNo, pageSize, sortField, sortDir);

    @GetMapping("/")
    public String home( Model model) {
        System.out.println("this is first");
        return findPaginated(1,"title","asc","",model);
    }

    @GetMapping("/home")
    public String findPaginated(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                @RequestParam(value = "sortField", defaultValue = "title") String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                Model model) {
        int pageSize = 10;
        System.out.println("pageNo="+pageNo+" sortField="+sortField+" sortDir="+sortDir+" keyword="+keyword);
        Page<Post> page;
        if(keyword != "") {
            page = postService.searchAndPaginatePost(keyword, pageNo, pageSize, sortField, sortDir);
        }
        else{
            page=postService.getPaginatedPost(pageNo, pageSize,sortField,sortDir);
        }
        List<Post> listPosts=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("listPosts",listPosts);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir);
        model.addAttribute("keyword",keyword);
        return "Home.html";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam("author") String author,
                       @RequestParam("publishedDate") String publishedDateTime,
                       @RequestParam("tags") String tags,
                       @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                       @RequestParam(value = "sortField", defaultValue = "title") String sortField,
                       @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword,Model model){
        System.out.println("hiii_____its working");

        int pageSize = 10;
        System.out.println("pageNo="+pageNo+" sortField="+sortField+" sortDir="+sortDir);
        System.out.println("author="+author+" publishedDateTime="+publishedDateTime+" tags="+tags+" keyword="+keyword);
        Page<Post> page;
        List<Post> listProducts;
        Timestamp publishedAt;
        /* Page<Post> page;
        if(keyword != "") {
            page = postService.searchAndPaginatePost(keyword, pageNo, pageSize, sortField, sortDir);
        }
        else{
            page=postService.getPaginatedPost(pageNo, pageSize,sortField,sortDir);
        }*/

        if(author != "" && publishedDateTime != "" && tags != ""){
            System.out.println("111111");
            publishedAt = Timestamp.valueOf(publishedDateTime);
             listProducts=postService.filterAll(author,publishedAt,tags);
            page = postService.filterAndPaginatePost(author,publishedAt,tags, pageNo, pageSize, sortField, sortDir);
        }
        else if(author != "" && publishedDateTime != "" ){
            System.out.println("2222222");
            publishedAt = Timestamp.valueOf(publishedDateTime);
             listProducts=postService.filterAll(author,publishedAt);
            page = postService.filterAndPaginatePost(author,publishedAt,pageNo, pageSize, sortField, sortDir);
        }
        else if(author != ""){
            System.out.println("3333333");
             listProducts=postService.filterAll(author);
            page = postService.filterAndPaginatePost(author,pageNo, pageSize, sortField, sortDir);
        }
        else{
            System.out.println("4444444444");
            listProducts=postService.listAll();
            page =postService.getPaginatedPost(pageNo,pageSize,sortField,sortDir);
        }
        //List<Post> listProducts=postService.filterAll(author,publishedDateTime,tags);
       for(int i=0;i<listProducts.size();i++){
            System.out.println(listProducts.get(i).getId());
            System.out.println(listProducts.get(i).getTitle());
            System.out.println(listProducts.get(i).getAuthor());
            System.out.println(listProducts.get(i).getContent());
        }

        List<Post> listPosts=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("listPosts",listPosts);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir);
        model.addAttribute("keyword",keyword);
        return "Home.html";

    }
}
