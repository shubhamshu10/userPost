package com.example.demo.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryDto {
	    private int categoryId;
        @NotEmpty(message="title should not empty")
	    private String categoryTitle;
        @NotEmpty(message="description should not empty")
        @Size(min=15,max=200)
	    private String categoryDescription;
		public CategoryDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}
		public String getCategoryTitle() {
			return categoryTitle;
		}
		public void setCategoryTitle(String categoryTitle) {
			this.categoryTitle = categoryTitle;
		}
		public String getCategoryDescription() {
			return categoryDescription;
		}
		public void setCategoryDescription(String categoryDescription) {
			this.categoryDescription = categoryDescription;
		}
}
