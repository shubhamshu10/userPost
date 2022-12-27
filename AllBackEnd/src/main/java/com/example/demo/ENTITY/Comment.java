package com.example.demo.ENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.NoArgsConstructor;
@NoArgsConstructor
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
     private int id;
     private String content;
     @ManyToOne
     private Post post;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	
}
