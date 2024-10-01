package com.codewithmanoj.blog.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithmanoj.blog.entity.Category;
import com.codewithmanoj.blog.entity.Post;
import com.codewithmanoj.blog.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
     //Custom finder methods 
	List<Post>findByUser(User user);
	List<Post>findByCategory(Category category);

	List<Post>findByTitleContaining(String title);
}
