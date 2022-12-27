package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.CategoryDto;

public interface CategoryService {
     CategoryDto createNewCategory(CategoryDto dto);
     CategoryDto updateCategory(CategoryDto dto,int id);
     void deleteCategoryById(int id);
     CategoryDto getCategory(int id);
     List<CategoryDto> findAllCategory();
}
