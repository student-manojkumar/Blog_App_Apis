package com.codewithmanoj.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithmanoj.blog.entity.User;

public interface  UserRepo extends JpaRepository<User, Integer> {
	
	

}
