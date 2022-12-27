package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CommentDto;
import com.example.demo.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	@Autowired
      private CommentService service;
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createCommentOnPost(@RequestBody CommentDto dto,@PathVariable("postId")int postId){
		CommentDto cDto = this.service.createComment(dto, postId);
		return new ResponseEntity<CommentDto>(cDto,HttpStatus.CREATED);		
	}
	@DeleteMapping("/delete/comment/{commentId}")
	public ApiResponse deleteComment(@PathVariable("commentId")int commentId) {
		this.service.deleteComment(commentId);
		return new ApiResponse("Comment deleted successfully", true); 
	}
}
