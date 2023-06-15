package com.rahul.blogapplication.services;

import com.rahul.blogapplication.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RestService {
    @Autowired
    private  PostService postService;
    public boolean check(Authentication authentication,int postId){

        if(authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }
        String loggedInUser = authentication.getName();
        Post post=postService.getPostById(postId);
        String author=post.getAuthor();
         if(loggedInUser.equals(author)){
             return true;
         }
        return false;
    }
}
