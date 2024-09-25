package com.mayxstudios.services;

import com.mayxstudios.dtos.CommentDTO;
import com.mayxstudios.dtos.CreateCommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getAllCommentsForPost(Long postId);


    CommentDTO createComment(CreateCommentDTO createCommentDTO, Long postId);


    CommentDTO updateComment(Long id, CommentDTO commentDTO);


    void deleteComment(Long id);
}