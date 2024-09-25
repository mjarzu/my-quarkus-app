package com.mayxstudios.services;

import com.mayxstudios.dtos.CreatePostDTO;
import com.mayxstudios.dtos.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();

    PostDTO getPost(Long id);

    PostDTO createPost(CreatePostDTO createPostDTO);

    PostDTO updatePost(Long id, PostDTO postDTO);

    void deletePost(Long id);
}