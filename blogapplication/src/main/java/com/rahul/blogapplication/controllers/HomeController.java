package com.rahul.blogapplication.controllers;

import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home( Model model) {
        return findPaginated(null,null,null,1,"title","asc",null,model);
    }
    @GetMapping("/home")
    public String findPaginated(@RequestParam(value="author",required = false) String author,
                         @RequestParam(value="publishedDate",required = false) String publishedDateTime,
                         @RequestParam(value="tags",required = false) String tags,
                         @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                         @RequestParam(value = "sortField", defaultValue = "title") String sortField,
                         @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                         @RequestParam(value = "keyword",required = false) String searchKeyword,Model model){

        int pageSize = 10;
        Page<Post> page;
        Timestamp publishedAt;
        List<Post> allPosts=postService.listAll();

        if(author != null && publishedDateTime != null && tags != null && author != "" && publishedDateTime != "" && tags != ""){
            publishedAt = Timestamp.valueOf(publishedDateTime);
            page = postService.filterAndPaginatePost(author,publishedAt,tags, pageNo, pageSize, sortField, sortDir);
        }
        else if(author != null && publishedDateTime != null && author != "" && publishedDateTime != ""){
            publishedAt = Timestamp.valueOf(publishedDateTime);
            page = postService.filterAndPaginatePost(author,publishedAt,pageNo, pageSize, sortField, sortDir);
        }
        else if(publishedDateTime != null && tags != null && publishedDateTime != "" && tags != "" ){
            publishedAt = Timestamp.valueOf(publishedDateTime);
            page=postService.filterAndPaginatePost(publishedAt,tags,pageNo, pageSize, sortField, sortDir);
        }
        else if(author != null && tags != null && author != "" && tags != ""){
            page=postService.filterAndPaginatePost(author,tags,pageNo, pageSize, sortField, sortDir);
        }
        else if(author != null && author != ""){
            page = postService.filterAndPaginatePost(author,pageNo, pageSize, sortField, sortDir);
        }
        else if(publishedDateTime != null && publishedDateTime != ""){
            publishedAt = Timestamp.valueOf(publishedDateTime);
            page = postService.filterAndPaginatePost(publishedAt,pageNo, pageSize, sortField, sortDir);
        }
        else if(tags != null && tags != ""){
            page = postService.TagFilterAndPaginatePost(tags,pageNo, pageSize, sortField, sortDir);
        }
       else if(searchKeyword != "" && searchKeyword != null&&searchKeyword !="null") {
            page = postService.searchAndPaginatePost(searchKeyword, pageNo, pageSize, sortField, sortDir);
        }
        else{
            page =postService.getPaginatedPost(pageNo,pageSize,sortField,sortDir);
        }

        List<Post> listPosts=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("listPosts",listPosts);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir);
        model.addAttribute("searchKeyword",searchKeyword);
        model.addAttribute("author",author);
        model.addAttribute("publishedDate",publishedDateTime);
        model.addAttribute("tags",tags);
        model.addAttribute("allPosts",allPosts);
        return "Home.html";
    }
}
