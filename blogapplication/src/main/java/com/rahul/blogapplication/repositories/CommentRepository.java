package com.rahul.blogapplication.repositories;

import com.rahul.blogapplication.models.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findByPostId(int postId);
    public Comment findById(int id);
    @Transactional
    public void deleteByPostId(Integer postId);
    @Transactional
    public List<Comment> deleteCommentsByPostId(Integer postId);
}
