package com.rahul.blogapplication.repositories;

import com.rahul.blogapplication.models.Comment;
import com.rahul.blogapplication.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findByPostId(int postId);
    public Comment findById(long id);


}
