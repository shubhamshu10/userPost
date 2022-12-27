package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ENTITY.Category;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.CategoryDto;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    
	@Override
	public CategoryDto createNewCategory(CategoryDto dto) {
		Category cate=this.modelMapper.map(dto, Category.class);
		Category addCate = this.repo.save(cate);
		return this.modelMapper.map(addCate, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto dto, int id) {
		Category cate = this.repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", id));
		cate.setCategoryTitle(dto.getCategoryTitle());
		cate.setCategoryDescription(dto.getCategoryDescription());
		Category updatedCate = this.repo.save(cate);
		return this.modelMapper.map(updatedCate, CategoryDto.class);
	}

	@Override
	public void deleteCategoryById(int id) {
		Category cate = this.repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", id));
		repo.delete(cate);
	}

	@Override
	public CategoryDto getCategory(int id) {
		Category cate = this.repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", id));
		
		return this.modelMapper.map(cate, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> findAllCategory() {
		List<Category> cate = this.repo.findAll();
		List<CategoryDto> dto = cate.stream().map((cat)->modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return dto;
	}
    
}
