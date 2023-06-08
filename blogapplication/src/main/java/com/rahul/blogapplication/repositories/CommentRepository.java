package com.rahul.blogapplication.repositories;

import com.rahul.blogapplication.models.Comment;
import com.rahul.blogapplication.models.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findByPostId(int postId);
    public Comment findById(int id);

    @Transactional
    void deleteByPostId(Integer postId);
}
