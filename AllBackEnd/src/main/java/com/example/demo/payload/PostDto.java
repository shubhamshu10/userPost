package com.example.demo.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.ENTITY.Category;
import com.example.demo.ENTITY.Comment;
import com.example.demo.ENTITY.User;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostDto {
	  private int postId;
      private String title;
      private String content;
      private String imageName;
      private Date addedDate;
      private User user;
      private Category category;
      private Set<Comment> comments=new HashSet<>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
      
}
