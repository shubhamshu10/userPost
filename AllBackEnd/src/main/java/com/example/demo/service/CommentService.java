package com.example.demo.service;

import com.example.demo.payload.CommentDto;

public interface CommentService {
     CommentDto createComment(CommentDto commentDto,int postId);
     void deleteComment(int commentId);
}
