package com.codewithmanoj.blog.payloads;

import java.sql.Date;

import com.codewithmanoj.blog.entity.Category;
import com.codewithmanoj.blog.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	private String title;
	
	private String content;
	
	private String imgeName;
	
	private Date addeDate;
	
	private CategoryDto category;
	
	private UserDto user;
	

}
