package com.codewithmanoj.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.codewithmanoj.blog.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
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

import com.codewithmanoj.blog.config.AppConstants;

import com.codewithmanoj.blog.payloads.ApiResponse;
import com.codewithmanoj.blog.payloads.PostDto;
import com.codewithmanoj.blog.payloads.PostResponse;
import com.codewithmanoj.blog.services.PostService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;


	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto
			,@PathVariable Integer userId
			,@PathVariable Integer categoryId){
		
		  PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		  return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	@GetMapping("/user/{userId}/posts")
	public  ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts=this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	//get by category
	
	@GetMapping("/category/{categoryId}/posts")
	public  ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		
		List<PostDto> posts=this.postService.getPostsByCategory(categoryId );
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	//get All post
	
	  @GetMapping("/posts")
	  public ResponseEntity<PostResponse> getAllpost(
			  @RequestParam(value ="pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
			  @RequestParam(value ="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			  @RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
			  @RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir
			  ){
		  
		  PostResponse postResponse =this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		    
		     return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	  }
	
	//get by id post
	

	  @GetMapping("/posts/{postId}")
	  public ResponseEntity<PostDto> getAllpostById(@PathVariable Integer postId){
		  
		  PostDto postDtos=this.postService.getPostById(postId);
		  
		  return new ResponseEntity<PostDto>(postDtos, HttpStatus.OK);
	  }
	  
	  //delete post
	  @DeleteMapping("/posts/{postId}")
	  public ApiResponse deletePost(@PathVariable Integer postId) {
		  
		    this.postService.deletePost(postId);
		    return new ApiResponse("Post is successfully deleted !!",true);
	  }
	  
	  
	  //update post
	  @PutMapping("/posts/{postId}")
	  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId) {
		  
		PostDto updatePost= this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	  }
	  
	  //search
	  @GetMapping("/posts/search/{Keyword}")
	  public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable("Keyword")String Keyword
	  ){
		 List<PostDto> result= this.postService.searchPosts(Keyword);
		 return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	  }

	  //post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImaege(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
	) throws IOException {
		// Update the post with the new image information
		PostDto postDto=this.postService.getPostById(postId);
		// Upload image
		String filename = this.fileService.uploadImage(path, image);

		// Set the image name in the post object
		postDto.setImgeName(filename);
		PostDto updatePost= this.postService.updatePost(postDto, postId);
		return  new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	//method to serve files
	@GetMapping(value="/post/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
	) throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName);

		 return	IOUtils.toByteArray(resource);


	}

}
