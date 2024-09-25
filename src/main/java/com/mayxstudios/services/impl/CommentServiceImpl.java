package com.mayxstudios.services.impl;

import com.mayxstudios.converters.CommentConverter;
import com.mayxstudios.dtos.CommentDTO;
import com.mayxstudios.dtos.CreateCommentDTO;
import com.mayxstudios.entities.Comment;
import com.mayxstudios.entities.Post;
import com.mayxstudios.exceptions.ResourceNotFoundException;
import com.mayxstudios.repositories.CommentRepository;
import com.mayxstudios.repositories.PostRepository;
import com.mayxstudios.services.CommentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentConverter commentConverter;

    @Inject
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    @Transactional
    public List<CommentDTO> getAllCommentsForPost(Long postId) {
        return commentRepository.find("post.id", postId).list().stream()
                .map(commentConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO createComment(CreateCommentDTO createCommentDTO, Long postId) {

        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new ResourceNotFoundException("Post with id " + postId + " not found.");
        }
        Comment comment = commentConverter.toEntity(createCommentDTO);
        comment.setPost(post);
        commentRepository.persist(comment);
        return commentConverter.toDTO(comment);
    }

    @Override
    @Transactional
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id " + id + " not found.");
        }

        Comment updatedComment = commentConverter.toEntity(commentDTO);
        updatedComment.setId(id);
        commentRepository.persist(updatedComment);

        return commentConverter.toDTO(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id " + id + " not found.");
        }
        commentRepository.delete(comment);
    }
}