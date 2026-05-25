package com.blogging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private String postId;
    private String title;
    private String imageName;
    private String content;
    private LocalDate createdAt;
    private UserDto user;
    private CategoryDto category;
}
