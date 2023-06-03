package com.rahul.blogapplication.services;

import com.rahul.blogapplication.dto.PostDto;
import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.models.Tag;
import com.rahul.blogapplication.repositories.PostRepository;
import com.rahul.blogapplication.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository  postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;

    public void addPost(PostDto postDto){
      Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setAuthor(postDto.getAuthorName());
        post.setContent(postDto.getContent());
        post.setExcerpt(postDto.getContent().substring(0,1));

        /*postRepository.save(post);
        tagService.addTag(postDto.getTags());*/

        String [] tagsArray=postDto.getTags().split(",");
        for (String tagName : tagsArray) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
               /*tag.setCreatedAt(LocalDateTime.now());
                tag.setUpdatedAt(LocalDateTime.now());*/
                tag = tagRepository.save(tag);
            }
            post.getTags().add(tag);
        }
        postRepository.save(post);
    }

    public Page<Post> getPaginatedPost(int page, int pageSize) {
        Pageable pageable =PageRequest.of(page - 1, pageSize);
        return postRepository.findAll(pageable);
    }

    public Post getPostById(int postId){
        return postRepository.findById(postId);
    }
}
