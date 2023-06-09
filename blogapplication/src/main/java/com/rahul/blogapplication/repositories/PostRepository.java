package com.rahul.blogapplication.repositories;

import com.rahul.blogapplication.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
        public Post findById(long id);
        @Query("SELECT p FROM Post p WHERE p.title LIKE %?1%"
          + " OR p.content LIKE %?1%"
          + " OR p.author LIKE %?1%"
          + " OR EXISTS (SELECT t FROM p.tags t WHERE t.name LIKE %?1%)")
        public List<Post> findAll(String keyword);
        @Query("SELECT p FROM Post p WHERE p.title LIKE %?1%"
          + " OR p.content LIKE %?1%"
          + " OR p.author LIKE %?1%"
          + " OR EXISTS (SELECT t FROM p.tags t WHERE t.name LIKE %?1%)")
        Page<Post> searchPosts(String keyword, Pageable pageable);
        @Query("SELECT p FROM Post p WHERE p.author = ?1 AND p.publishedAt = ?2 AND EXISTS (SELECT t FROM p.tags t WHERE t.name LIKE %?3%)")
        Page<Post> findByAuthorAndPublishedAtAndTags(String author, Timestamp publishedAt, String tags, Pageable pageable);
        @Query("SELECT p FROM Post p WHERE p.publishedAt = ?1 AND EXISTS (SELECT t FROM p.tags t WHERE t.name LIKE %?2%)")
        Page<Post> findByPublishedAtAndTags(Timestamp publishedAt,String tags,Pageable pageable);
        Page<Post> findByAuthorAndPublishedAt(String author, Timestamp publishedAt,Pageable pageable);
        @Query("SELECT p FROM Post p WHERE p.author = ?1 AND EXISTS (SELECT t FROM p.tags t WHERE t.name LIKE %?2%)")
        Page<Post> findByAuthorAndTags(String author,String tags,Pageable pageable);
        Page<Post> findByAuthor(String author,Pageable pageable);
        Page<Post> findByPublishedAt(Timestamp publishedAt,Pageable pageable);
        @Query("SELECT p FROM Post p WHERE EXISTS (SELECT t FROM p.tags t WHERE t.name LIKE %?1%)")
        Page<Post> findByTags(String tags,Pageable pageable);
}
