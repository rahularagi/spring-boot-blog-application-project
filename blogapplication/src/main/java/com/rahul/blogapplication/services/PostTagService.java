package com.rahul.blogapplication.services;

import com.rahul.blogapplication.repositories.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTagService {
    @Autowired
    private PostTagRepository postTagRepository;
   public void deletePostIdInPostTag(int postId){
      postTagRepository.deleteByPost_Id(postId);
   }
}
