package com.codewithmanoj.blog.controllers;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmanoj.blog.payloads.ApiResponse;
import com.codewithmanoj.blog.payloads.ApiResponse;
import com.codewithmanoj.blog.payloads.UserDto;
import com.codewithmanoj.blog.services.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/users")

public class UserController {

	@Autowired
	private UserService userService;
	 //post-create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
		
	}
	
	//put-update user or path uri variable
	@PutMapping("/{userId}")
	public  ResponseEntity<UserDto> UpdateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		
		UserDto updateedUser =this.userService.updateFUser(userDto, uid);
		return ResponseEntity.ok(updateedUser);
	}
	// Delete delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		
		this.userService.deleteUser(uid);
		//return new ResponseEntity(Map.of("message", "User Deleted Successfully"),HttpStatus.OK);
		
		return new ResponseEntity<ApiResponse> (new ApiResponse( "User Deleted Successfully",true), HttpStatus.OK);
		
	}
	//Get user get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	//Get user by id
		@GetMapping("/{userId}")
		public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId){
			
			return ResponseEntity.ok(this.userService.getUserBytId(userId));
		}
	
}
