package com.blogging.Repository;

import com.blogging.Model.Category;
import com.blogging.Model.Post;
import com.blogging.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,String> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContainingIgnoreCase(String title);
}
