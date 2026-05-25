package com.blogging.Service;

import com.blogging.Exception.ResourceNotFoundException;
import com.blogging.Model.Comment;
import com.blogging.Model.Post;
import com.blogging.Model.User;
import com.blogging.Repository.CommentRepository;
import com.blogging.Repository.PostRepository;
import com.blogging.Repository.UserRepository;
import com.blogging.dto.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public CommentDto createComment(CommentDto commentDto, String postId, String userId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        CommentDto response = modelMapper.map(savedComment, CommentDto.class);
        response.setPostId(postId);
        response.setPostTitle(post.getTitle());
        response.setUserId(userId);
        response.setUserName(user.getName());

        return response;
    }

    public List<CommentDto> getAllComment(){
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).toList();
    }

    public void deleteComment(String commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }
}
