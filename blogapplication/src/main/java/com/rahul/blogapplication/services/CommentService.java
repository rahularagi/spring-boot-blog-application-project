package com.rahul.blogapplication.services;

import com.rahul.blogapplication.models.Comment;
import com.rahul.blogapplication.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    public List<Comment> getCommentsByPostId(int postId){
        return commentRepository.findByPostId(postId);
    }
    public void addComment(Comment newComment){
        commentRepository.save(newComment);
    }
    public Comment getCommentById(int commentId){
       return commentRepository.findById(commentId);
    }
    public void deleteCommentByID(int commentId){
        commentRepository.deleteById(commentId);
    }
    public void deleteCommentByPostId(int postId){
       commentRepository.deleteByPostId(postId);
    }

    public List<Comment> deleteCommentsByPostId(int postId){
        return commentRepository.deleteCommentsByPostId(postId);
    }

    public Comment saveComment(Comment newComment){
        return commentRepository.save(newComment);
    }
    public List<Comment> allComments(){
        return commentRepository.findAll();
    }

    public Comment deleteCommentByCommentID(int commentId){
        Comment comment =commentRepository.findById(commentId);
        commentRepository.deleteById(commentId);
        return comment;
    }
}

