package com.blogging.Controller;

import com.blogging.Service.FileService;
import com.blogging.Service.PostService;
import com.blogging.config.AppConstants;
import com.blogging.dto.PostDto;
import com.blogging.dto.PostResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class PostController {

    private final PostService postService;
    private  final FileService fileService;
    
    @Value("${project.image}")
    private String path;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable String userId,
                                              @PathVariable String categoryId){

       return ResponseEntity.status(HttpStatus.CREATED).body( postService.createPost(postDto,userId,categoryId));
    }

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable String userId){
        return ResponseEntity.ok().body(postService.getAllPostByUser(userId));
    }

    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable String categoryId){
        return ResponseEntity.ok().body(postService.getAllPostByCategory(categoryId));
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> getPostByKeyword(@PathVariable String keyword){
        return ResponseEntity.ok().body(postService.searchPost(keyword));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable String postId){
        return ResponseEntity.ok().body(postService.getPostById(postId));
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize" ,defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sourBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
            ){
        return ResponseEntity.ok().body(postService.getAllPost(pageNumber,pageSize,sortBy,sortDir));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable String postId){
        return ResponseEntity.ok().body(postService.updatePost(postDto,postId));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().body(Map.of("message","Post deleted Successfully"));
    }
    
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable String postId) throws IOException {

        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatePost);
    }
}
