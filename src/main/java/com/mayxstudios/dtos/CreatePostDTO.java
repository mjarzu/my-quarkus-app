package com.mayxstudios.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostDTO {
    private String title;
    private String author;
    private String content;
}
