package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.ENTITY.Category;
import com.example.demo.ENTITY.Post;
import com.example.demo.ENTITY.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.PostDto;
import com.example.demo.payload.PostResponse;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PostService;
@Service
public class PostServiceImpl implements PostService {
	@Autowired
    private PostRepository postRepo;
	@Autowired
    private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CategoryRepository cateRepo;
	@Override
	public PostDto createPost(PostDto postDto,int userId,int cateId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user_id", userId));
		Category cate = this.cateRepo.findById(cateId).orElseThrow(()-> new ResourceNotFoundException("Category", "category_id", cateId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(cate);
		post.setUser(user);
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int post_id) {
		Post post = this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post", "post_id", post_id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatePost = this.postRepo.save(post);
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public PostDto getPostById(int post_id) {
		Post post = this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post", "post_id", post_id));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(int post_id) {
		Post post = this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("post", "post_id", post_id));
		this.postRepo.deleteById(post_id);
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable pg=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost= this.postRepo.findAll(pg);
		List<Post> post = pagePost.getContent();
		List<PostDto> dto=post.stream().map((posts)-> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		
		PostResponse resp = new PostResponse();
		resp.setContent(dto);
		resp.setPageNumber(pagePost.getNumber());
		resp.setPageSize(pagePost.getSize());
		resp.setTotalElements(pagePost.getNumberOfElements());
		resp.setTotalPages(pagePost.getTotalPages());
		resp.setLastPage(pagePost.isLast());
		return resp;
	}

	@Override
	public List<PostDto> getByUser(int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User_id", userId));
		List<Post> post=this.postRepo.findByUser(user);
		List<PostDto> dto = post.stream().map((posts)->  this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList()); 
		return dto;
	}

	@Override
	public List<PostDto> getByCategory(int cateId) {
		Category cate = this.cateRepo.findById(cateId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", cateId));
		List<Post> post = this.postRepo.findByCategory(cate);
		List<PostDto> dto= post.stream().map((posts)->  this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return dto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
	    List<Post> posts=this.postRepo.findByTitleContaining(keyword);
	    List<PostDto> dto=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return dto;
	}

	

}
