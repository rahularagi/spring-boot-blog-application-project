package com.rahul.blogapplication.services;

import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository  postRepository;

    public void addPost(Post post){
        postRepository.save(post);
    }
}
