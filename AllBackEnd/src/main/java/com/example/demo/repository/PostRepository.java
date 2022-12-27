package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.ENTITY.Category;
import com.example.demo.ENTITY.Post;
import com.example.demo.ENTITY.User;

public interface PostRepository extends JpaRepository<Post,Integer>{
     List<Post> findByUser(User user);
     List<Post> findByCategory(Category category);
     List<Post> findByTitleContaining(String title);
}
