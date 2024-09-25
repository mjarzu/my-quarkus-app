package com.mayxstudios.converters;

import com.mayxstudios.dtos.CommentDTO;
import com.mayxstudios.dtos.CreateCommentDTO;
import com.mayxstudios.entities.Comment;
import com.mayxstudios.entities.Post;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommentConverter implements EntityConverter<Comment, CommentDTO> {

    @Override
    public CommentDTO toDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .build();
    }

    @Override
    public Comment toEntity(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .id(commentDTO.getId()) // If updating, this is needed
                .author(commentDTO.getAuthor())
                .content(commentDTO.getContent())
                .build();

        Post post = new Post();
        post.setId(commentDTO.getPostId());
        comment.setPost(post);

        return comment;
    }

    public Comment toEntity(CreateCommentDTO createCommentDTO) {
        return Comment.builder()
                .author(createCommentDTO.getAuthor())
                .content(createCommentDTO.getContent())
                .build();
    }
}