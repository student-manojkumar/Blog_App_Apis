package com.codewithmanoj.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithmanoj.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
