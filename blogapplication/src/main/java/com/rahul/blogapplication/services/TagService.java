package com.rahul.blogapplication.services;

import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.models.Tag;
import com.rahul.blogapplication.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

}
