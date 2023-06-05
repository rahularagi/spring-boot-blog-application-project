package com.rahul.blogapplication.dto;

import com.rahul.blogapplication.models.Post;
import com.rahul.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;

public class PostDto {
    private String title;
    private String tags;
    private String authorName;
    private String content;
    private int id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
