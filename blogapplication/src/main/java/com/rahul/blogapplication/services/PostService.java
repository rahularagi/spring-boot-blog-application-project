package com.rahul.blogapplication.services;

import com.rahul.blogapplication.dto.PostDto;
import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.models.Tag;
import com.rahul.blogapplication.repositories.PostRepository;
import com.rahul.blogapplication.repositories.PostTagRepository;
import com.rahul.blogapplication.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private PostTagService postTagService;

    public void addPost(PostDto postDto){
      Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setAuthor(postDto.getAuthorName());
        post.setContent(postDto.getContent());
        post.setExcerpt(postDto.getContent().substring(0,1));
        if(postDto.getId()>0){
            post.setId(postDto.getId());
        }

        String [] tagsArray=postDto.getTags().split(",");
        for (String tagName : tagsArray) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                tag = tagRepository.save(tag);
            }
            post.getTags().add(tag);
        }
        postRepository.save(post);
    }

    public PostDto getPostDto(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setAuthorName(post.getAuthor());
        postDto.setContent(post.getContent());
        String tag="";
        List<Tag> tags=post.getTags();
        for(int i=0;i<tags.size();i++) {
            Tag tagObj=tags.get(i);
            tag+=tagObj.getName();
            if(i<tags.size()-1){
                tag+=",";
        }
         postDto.setTags(tag);
        }
        return postDto;
    }

    public Page<Post> getPaginatedPost(int page, int pageSize,String sortField,String sortDirection) {
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():Sort.by(sortField).descending();
        Pageable pageable =PageRequest.of(page - 1, pageSize,sort);
        return postRepository.findAll(pageable);
    }

    public Post getPostById(int postId){
        return postRepository.findById(postId);
    }
    public void deletePostByID(int postId){
        postTagService.deletePostIdInPostTag(postId);
        postRepository.deleteById(postId);
    }
}
