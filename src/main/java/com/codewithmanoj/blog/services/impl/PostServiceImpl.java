package com.codewithmanoj.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithmanoj.blog.Repositories.CategoryRepo;
import com.codewithmanoj.blog.Repositories.PostRepo;
import com.codewithmanoj.blog.Repositories.UserRepo;
import com.codewithmanoj.blog.entity.Category;
import com.codewithmanoj.blog.entity.Post;
import com.codewithmanoj.blog.entity.User;
import com.codewithmanoj.blog.exceptions.ResourceNotFoundException;
import com.codewithmanoj.blog.payloads.PostDto;
import com.codewithmanoj.blog.payloads.PostResponse;
import com.codewithmanoj.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","User id",userId));
		
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("User","User id", categoryId));
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImgeName("default.png");
		post.setAddeDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost=this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post =this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post","post id ", postId));
		   post.setTitle(postDto.getTitle());
		   post.setContent(postDto.getContent());
		   post.setImgeName(postDto.getImgeName());
		   
		   Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post =this.postRepo.findById(postId)
		.orElseThrow(()-> new ResourceNotFoundException("Post","post id ", postId));
         this.postRepo.delete(post);
	}

	/*@Override
	public List<PostDto> getAllPost() {
		 List<Post>allPosts=this.postRepo.findAll();
		List<PostDto>postDtos= allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}*/

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort );
		
        Page<Post> pagePost = this.postRepo.findAll(pageable);
       
        List<Post> allPosts = pagePost.getContent();

        List<PostDto> postDtos = allPosts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements());
        
        
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        
        return postResponse;
	}
	
	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		Category cat=this.categoryRepo.findById(categoryId)
				        .orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
		 List<Post> posts= this.postRepo.findByCategory(cat);
		 
		  List<PostDto> postDto =posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		 
		return postDto;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
		
		List<Post>users=this.postRepo.findByUser(user);
		
		 List<PostDto> postsDtos = users.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDtos;
	}

	@Override
	public List<PostDto> searchPosts(String Keyword) {
		
		List<Post> posts= this.postRepo.findByTitleContaining(Keyword);
		List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	public List<PostDto> getPostCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

}
