package com.mayxstudios.services.impl;

import com.mayxstudios.converters.PostConverter;
import com.mayxstudios.dtos.CreatePostDTO;
import com.mayxstudios.dtos.PostDTO;
import com.mayxstudios.entities.Post;
import com.mayxstudios.exceptions.ResourceNotFoundException;
import com.mayxstudios.repositories.PostRepository;
import com.mayxstudios.services.PostService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    @Inject
    public PostServiceImpl(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Override
    @Transactional
    public List<PostDTO> getAllPosts() {
        return postRepository.listAll().stream()
                .map(postConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new ResourceNotFoundException("Post with id " + id + " not found.");
        }
        return postConverter.toDTO(post);
    }

    @Override
    @Transactional
    public PostDTO createPost(CreatePostDTO postDTO) {
        Post post = postConverter.toEntity(postDTO);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.persist(post);
        return postConverter.toDTO(post);
    }

    @Override
    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new ResourceNotFoundException("Post with id " + id + " not found.");
        }
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        postRepository.persist(post);
        return postConverter.toDTO(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new ResourceNotFoundException("Post with id " + id + " not found.");
        }
        postRepository.delete(post);
    }
}