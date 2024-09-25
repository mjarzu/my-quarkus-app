package com.mayxstudios.converters;

import com.mayxstudios.dtos.CreatePostDTO;
import com.mayxstudios.dtos.PostDTO;
import com.mayxstudios.entities.Post;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostConverter implements EntityConverter<Post, PostDTO> {

    @Override
    public PostDTO toDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .content(post.getContent())
                .build();
    }

    @Override
    public Post toEntity(PostDTO postDTO) {
        return Post.builder()
                .id(postDTO.getId()) // If updating, this is needed
                .title(postDTO.getTitle())
                .author(postDTO.getAuthor())
                .content(postDTO.getContent())
                .build();
    }

    public Post toEntity(CreatePostDTO createPostDTO) {
        return Post.builder()
                .title(createPostDTO.getTitle())
                .author(createPostDTO.getAuthor())
                .content(createPostDTO.getContent())
                .build();
    }
}