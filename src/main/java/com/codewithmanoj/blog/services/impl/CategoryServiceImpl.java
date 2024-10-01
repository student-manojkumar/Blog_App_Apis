package com.codewithmanoj.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithmanoj.blog.Repositories.CategoryRepo;
import com.codewithmanoj.blog.entity.Category;
import com.codewithmanoj.blog.exceptions.ResourceNotFoundException;
import com.codewithmanoj.blog.payloads.CategoryDto;
import com.codewithmanoj.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
     private CategoryRepo categoryRepo;
	 @Autowired
	private ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		Category addedCat= this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("category", "Category Id", categoryId));
		
		 cat.setCategoryTitle(categoryDto.getCategoryTitle());
		 
		 cat.setCategoryDescription(categoryDto.getCategoryDescription());
		 Category updatedcat=this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","Category id",categoryId));
		        this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","category Id", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
         
        List<Category> categories=this.categoryRepo.findAll();
       List<CategoryDto> catDtos=categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}

}
