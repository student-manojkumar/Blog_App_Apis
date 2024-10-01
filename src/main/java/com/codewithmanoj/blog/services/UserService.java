package com.codewithmanoj.blog.services;

import java.util.List;

import com.codewithmanoj.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateFUser(UserDto user ,Integer userId);
	UserDto getUserBytId(Integer userId);
	
	List<UserDto>getAllUser();
    void deleteUser(Integer userId);
}
