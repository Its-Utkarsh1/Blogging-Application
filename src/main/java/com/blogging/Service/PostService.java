package com.blogging.Service;

import com.blogging.Exception.ResourceNotFoundException;
import com.blogging.Model.Category;
import com.blogging.Model.Post;
import com.blogging.Model.User;
import com.blogging.Repository.CategoryRepository;
import com.blogging.Repository.PostRepository;
import com.blogging.Repository.UserRepository;
import com.blogging.dto.PostDto;
import com.blogging.dto.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public PostDto createPost(PostDto postDto, String userId, String categoryId){

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Post post = modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setCreatedAt(LocalDate.now());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }

    public PostDto updatePost(PostDto postDto, String id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        post.setTitle(post.getTitle());
        post.setContent(post.getContent());
        post.setImageName(post.getImageName());

        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(String id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return modelMapper.map(post,PostDto.class);
    }

    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir){

        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else{
            sort = Sort.by(sortBy).descending();
        }
        PageRequest p = PageRequest.of(pageNumber,pageSize,sort);

        Page<Post> posts = postRepository.findAll(p);

        List<Post> allPosts = posts.getContent();

        List<PostDto> postDtos = allPosts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement((int) posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());

        return postResponse;

    }

    public List<PostDto> getAllPostByCategory(String categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        List<Post> posts = postRepository.findByCategory(category);
        return posts.stream().map(post -> modelMapper.map(post,PostDto.class)).toList();
    }

    public List<PostDto> getAllPostByUser(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
    }

    public List<PostDto> searchPost(String keyword){
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(keyword);
        return posts.stream().map(post -> modelMapper.map(post,PostDto.class)).toList();
    }

    public void deletePost(String id){
        postRepository.deleteById(id);
    }


}
