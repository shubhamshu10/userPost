package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.PostDto;
import com.example.demo.payload.PostResponse;

public interface PostService {
   PostDto createPost(PostDto postDto,int userId,int cateId);
   PostDto updatePost(PostDto postDto, int post_id);
   PostDto getPostById(int post_id);
   void deletePost(int post_id);
   PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);
   List<PostDto> getByUser(int user_id);
   List<PostDto> getByCategory(int category_id);
   List<PostDto> searchPosts(String keyword);

}
