package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CategoryDto;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService service;
	@PostMapping("/")
     public ResponseEntity<CategoryDto> createDto(@Valid @RequestBody CategoryDto dto){
		CategoryDto createCategory=this.service.createNewCategory(dto);
    	return new ResponseEntity<>(createCategory,HttpStatus.CREATED);    	 
     }
	@PutMapping("/update/{id}")
	 public ResponseEntity<CategoryDto> updateDto(@Valid @RequestBody CategoryDto dto, @PathVariable("id")int id){
		 CategoryDto updateCategory = this.service.updateCategory(dto, id);
		 return ResponseEntity.ok(updateCategory);
	 }
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAll(){
		List<CategoryDto> dto = this.service.findAllCategory();
		return ResponseEntity.ok(dto);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getDto(@PathVariable("id") int id){
		CategoryDto dto = this.service.getCategory(id);
		return ResponseEntity.ok(dto);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") int id){
		this.service.deleteCategoryById(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true),HttpStatus.OK);
	}
}
