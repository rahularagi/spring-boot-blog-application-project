package com.rahul.blogapplication.controllers;

import com.rahul.blogapplication.dto.PostDto;
import com.rahul.blogapplication.models.Comment;
import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.services.CommentService;
import com.rahul.blogapplication.services.PostService;
import com.rahul.blogapplication.services.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.TreeSet;

@RestController
@RequestMapping("/api")
public class ControllerRest {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RestService restService;

    @GetMapping("/posts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/posts/{postId}")
    public PostDto getPostByID(@PathVariable String postId){
         Post post=postService.getPostById(Integer.parseInt(postId));
        return postService.getPostDto(post);
    }
    @DeleteMapping("/posts/delete/{postId}")
    public ResponseEntity<String> deletePostByID(@PathVariable String postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(restService.check(authentication,Integer.parseInt(postId))) {
            Post post = postService.deletePost(Integer.parseInt(postId));
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Un-Authorized", HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/posts/update/{postId}")
    public ResponseEntity<String> updatePostByID(@PathVariable int postId, @RequestBody PostDto postDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(restService.check(authentication,postId)) {
            postDto.setId(postId);
            PostDto resultPostDto = postService.updatePost(postDto);
            return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Un-Authorized", HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/posts/new")
    public PostDto savePost(@RequestBody PostDto postDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        postDto.setAuthorName(authentication.getName());
        return postService.updatePost(postDto);
    }
    @GetMapping("/pagination")
    public List<Post> findPaginated(@RequestParam(value="author",required = false) String selectedAuthor,
                                @RequestParam(value="publishedDate",required = false) String selectedPublishedDate,
                                @RequestParam(value="tags",required = false) String selectedTag,
                                @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                @RequestParam(value = "sortField", defaultValue = "title") String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                @RequestParam(value = "keyword",required = false) String searchKeyword, Model model){

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
        return listPosts;
    }
    @GetMapping("/comment")
    public List<Comment> viewAllComments(){
        return commentService.allComments();
    }
    @GetMapping("/comment/{postId}")
    public List<Comment> viewCommentsByPostId(@PathVariable int postId){
        return commentService.getCommentsByPostId(postId);
    }
    @DeleteMapping("/comment/delete/{postId}")
    public ResponseEntity<String> deleteCommentByPostID(@PathVariable int postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(restService.check(authentication,postId)) {
            List<Comment> comment=commentService.deleteCommentsByPostId(postId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Un-Authorized", HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("/comment/delete")
    public ResponseEntity<String> deleteCommentByCommentID(@RequestParam("commentId") int commentId){
        Comment comment=commentService.getCommentById(commentId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(restService.check(authentication,comment.getPostId())) {
            Comment commentResult= commentService.deleteCommentByCommentID(commentId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Un-Authorized", HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/comment/new/{postId}")
    public Comment addCommentByPostId(@PathVariable int postId,@RequestBody Comment comment){
        comment.setPostId(postId);
        return commentService.saveComment(comment);
    }
    @PutMapping("/comment/update/{commentId}")
    public ResponseEntity<String> updateCommentByID(@PathVariable int commentId, @RequestBody Comment comment){
        Comment commentSearch=commentService.getCommentById(commentId);
        comment.setPostId(commentSearch.getPostId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(restService.check(authentication,commentSearch.getPostId())) {
            comment.setId(commentId);
            Comment commentResult= commentService.saveComment(comment);
            return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Un-Authorized", HttpStatus.UNAUTHORIZED);
    }
}
