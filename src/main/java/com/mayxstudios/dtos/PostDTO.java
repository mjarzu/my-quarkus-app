package com.mayxstudios.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
}