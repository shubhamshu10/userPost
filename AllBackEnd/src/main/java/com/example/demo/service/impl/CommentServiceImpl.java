package com.example.demo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ENTITY.Comment;
import com.example.demo.ENTITY.Post;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.CommentDto;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository repo;
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		Comment comment = this.modelMapper.map(commentDto,Comment.class );
		comment.setPost(post);
		Comment savedComment = this.repo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment=this.repo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.repo.delete(comment);
	}

}
