package com.blogging.Controller;

import com.blogging.Service.CommentService;
import com.blogging.dto.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable String postId,@PathVariable String userId){
        return  ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentDto,postId,userId));
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(){
        return ResponseEntity.ok().body(commentService.getAllComment());
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(Map.of("message","Comment deleted Successfully"));
    }
}
