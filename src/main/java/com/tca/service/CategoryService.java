package com.tca.service;

import java.util.List;

import com.tca.entity.Category;

public interface CategoryService {

    boolean addCategory(Category category);

    boolean updateCategory(Category category);

    boolean deleteCategory(Long categoryId);

    Category getCategoryById(Long categoryId);

    Category getCategoryByName(String categoryName);

    List<Category> getAllCategories();

    List<Category> getActiveCategories();

}