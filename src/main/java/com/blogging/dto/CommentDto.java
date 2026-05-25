package com.blogging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String id;
    private String content;

    private String postId;
    private String postTitle;

    private String userId;
    private String userName;
}
