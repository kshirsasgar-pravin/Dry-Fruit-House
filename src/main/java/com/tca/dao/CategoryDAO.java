package com.tca.dao;

import java.util.List;

import com.tca.entity.Category;

public interface CategoryDAO {
	

	    boolean saveCategory(Category category);

	    boolean updateCategory(Category category);

	    boolean deleteCategory(Long categoryId);

	    Category getCategoryById(Long categoryId);

	    Category getCategoryByName(String categoryName);

	    List<Category> getAllCategories();

	    List<Category> getActiveCategories();
	
}
