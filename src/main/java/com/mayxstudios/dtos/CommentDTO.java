package com.mayxstudios.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String author;
    private String content;
    private Long postId;
}