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
import java.util.TreeSet;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home( Model model) {
        return findPaginated(null,null,null,1,"title","asc",null,model);
    }
    @GetMapping("/home")
    public String findPaginated(@RequestParam(value="author",required = false) String selectedAuthor,
                         @RequestParam(value="publishedDate",required = false) String selectedPublishedDate,
                         @RequestParam(value="tags",required = false) String selectedTag,
                         @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                         @RequestParam(value = "sortField", defaultValue = "title") String sortField,
                         @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                         @RequestParam(value = "keyword",required = false) String searchKeyword,Model model){

        int pageSize = 10;
        Page<Post> page;
        Timestamp publishedAt;
       TreeSet<String> allAuthors=postService.getAllAuthor();
       TreeSet<Timestamp> allPublishedAt=postService.getAllPublishedAt();
       TreeSet<String> allTags=postService.getAllTags();

        if(selectedAuthor != null && selectedPublishedDate != null && selectedTag != null && selectedAuthor != "" && selectedPublishedDate != "" && selectedPublishedDate != ""){
            publishedAt = Timestamp.valueOf(selectedPublishedDate);
            page = postService.filterAndPaginatePost(selectedAuthor,publishedAt,selectedTag, pageNo, pageSize, sortField, sortDir);
        }
        else if(selectedAuthor != null && selectedPublishedDate != null && selectedAuthor != "" && selectedPublishedDate != ""){
            publishedAt = Timestamp.valueOf(selectedPublishedDate);
            page = postService.filterAndPaginatePost(selectedAuthor,publishedAt,pageNo, pageSize, sortField, sortDir);
        }
        else if(selectedPublishedDate != null && selectedTag != null && selectedPublishedDate != "" && selectedTag != "" ){
            publishedAt = Timestamp.valueOf(selectedPublishedDate);
            page=postService.filterAndPaginatePost(publishedAt,selectedTag,pageNo, pageSize, sortField, sortDir);
        }
        else if(selectedAuthor != null && selectedTag != null && selectedAuthor != "" && selectedTag != ""){
            page=postService.filterAndPaginatePost(selectedAuthor,selectedTag,pageNo, pageSize, sortField, sortDir);
        }
        else if(selectedAuthor != null && selectedAuthor != ""){
            page = postService.filterAndPaginatePost(selectedAuthor,pageNo, pageSize, sortField, sortDir);
        }
        else if(selectedPublishedDate != null && selectedPublishedDate != ""){
            publishedAt = Timestamp.valueOf(selectedPublishedDate);
            page = postService.filterAndPaginatePost(publishedAt,pageNo, pageSize, sortField, sortDir);
        }
        else if(selectedTag != null && selectedTag != ""){
            page = postService.TagFilterAndPaginatePost(selectedTag,pageNo, pageSize, sortField, sortDir);
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
        model.addAttribute("selectedAuthor",selectedAuthor);
        model.addAttribute("selectedPublishedDate",selectedPublishedDate);
        model.addAttribute("selectedTag",selectedTag);
        model.addAttribute("allAuthors",allAuthors);
        model.addAttribute("allPublishedAt",allPublishedAt);
        model.addAttribute("allTags",allTags);
        return "Home.html";
    }
}
