package com.mayxstudios.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentDTO {
    private String author;
    private String content;
}