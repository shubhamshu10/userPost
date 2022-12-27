package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.ApplicationConstant;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.PostDto;
import com.example.demo.payload.PostResponse;
import com.example.demo.service.FileService;
import com.example.demo.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
    private PostService service;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	@PostMapping("/users/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto dto,@PathVariable("userId") int userId,@PathVariable("categoryId") int categoryId){
		PostDto createPost = this.service.createPost(dto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);		
	}
	@GetMapping("users/{userId}/post")
	public ResponseEntity<List<PostDto>> findByUser(@PathVariable("userId")int userId){
		List<PostDto> dto = this.service.getByUser(userId);
		return ResponseEntity.ok(dto);				
	}
	@GetMapping("category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> findByCategory(@PathVariable("categoryId")int categoryId){
		List<PostDto> dto = this.service.getByCategory(categoryId);
		return ResponseEntity.ok(dto);				
	}
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam (value="pageNumber",defaultValue=ApplicationConstant.PAGENUMBER,required=false)int pageNumber,
			@RequestParam(value="pageSize",defaultValue=ApplicationConstant.PAGESIZE,required=false)int pageSize,
			@RequestParam(value="sortBy",defaultValue=ApplicationConstant.SORTBY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=ApplicationConstant.SORTDIR,required=false)String sortDir){
		PostResponse dto = this.service.getAllPost( pageNumber, pageSize, sortBy,sortDir);
		return ResponseEntity.ok(dto);
	}
	@GetMapping("posts/{id}")
	public ResponseEntity<PostDto> getPost(@PathVariable("id")int id){
        PostDto dto = this.service.getPostById(id);
        return ResponseEntity.ok(dto);
	}
	@PutMapping("update/posts/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto dto, @PathVariable("id")int id){
		PostDto post = this.service.updatePost(dto, id);
		return ResponseEntity.ok(post);
	}
	@DeleteMapping("/delete/posts/{id}")
	public ApiResponse deletePost(@PathVariable("id")int id) {
		this.service.deletePost(id);
		return new ApiResponse("Post deleted successfully",true);
	}
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
	    List<PostDto> result=this.service.searchPosts(keywords);
	    return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable int postId) throws IOException{
	    PostDto postDto= this.service.getPostById(postId);
	    String fileName = this.fileService.uploadImage(path, image);
	    postDto.setImageName(fileName);
	    PostDto update = this.service.updatePost(postDto, postId);
	    
		return new ResponseEntity<PostDto>(update,HttpStatus.OK);
		
	}
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
}
