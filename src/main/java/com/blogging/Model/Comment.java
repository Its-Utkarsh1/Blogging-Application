package com.blogging.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
