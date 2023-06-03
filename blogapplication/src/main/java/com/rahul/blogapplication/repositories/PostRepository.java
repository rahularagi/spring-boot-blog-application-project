package com.rahul.blogapplication.repositories;

import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findById(long id);
}
