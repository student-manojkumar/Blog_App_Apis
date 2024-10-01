package com.codewithmanoj.blog.services;

import java.util.List;

import com.codewithmanoj.blog.entity.Post;
import com.codewithmanoj.blog.payloads.PostDto;
import com.codewithmanoj.blog.payloads.PostResponse;
import org.springframework.stereotype.Service;


public interface PostService {
	//create
	PostDto createPost(PostDto postDto, Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//Delete
	void deletePost(Integer postId);
	
	//get all posts
	//List<PostDto>getAllPost(Integer pageNumber, Integer pageSize);
	 PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	//get single post
	 PostDto getPostById(Integer postId);
	 
	 //get all post by Category
	 List<PostDto>getPostsByCategory(Integer categoryId);
	 
	 //get all post by user
	 List<PostDto>getPostsByUser(Integer userId);
	 
	 //Search Posts
	  List<PostDto> searchPosts(String Keyword);

	
}
