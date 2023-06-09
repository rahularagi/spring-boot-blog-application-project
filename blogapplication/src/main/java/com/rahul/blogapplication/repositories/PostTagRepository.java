package com.rahul.blogapplication.repositories;

import com.rahul.blogapplication.models.PostTag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {
    @Transactional
    void deleteByPost_Id(Integer postId);
}
