package com.DryFruitHouse.service.impl;

import java.util.List;

import com.DryFruitHouse.repository.CategoryDAO;
import com.DryFruitHouse.repository.impl.CategoryDAOImpl;
import com.DryFruitHouse.entity.Category;
import com.DryFruitHouse.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {


    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public boolean addCategory(Category category) {
        if (category == null ||
        	    category.getCategoryName() == null || 
        		category.getCategoryName().trim().isEmpty()) {
            return false;
        }

        if (categoryDAO.getCategoryByName(category.getCategoryName().trim()) != null) {
            return false;
        }

        return categoryDAO.saveCategory(category);
    }

    @Override
    public boolean updateCategory(Category category) {
        if (category == null || category.getCategoryId() == null) {
            return false;
        }

        Category existingCategory = categoryDAO.getCategoryById(category.getCategoryId());
        if (existingCategory == null) {
            return false;
        }

        if (category.getCategoryName() != null) {
            Category duplicateCheck = categoryDAO.getCategoryByName(category.getCategoryName().trim());
            if (duplicateCheck != null && !duplicateCheck.getCategoryId().equals(category.getCategoryId())) {
                return false;
            }
        }

        return categoryDAO.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(Long categoryId) {
        if (categoryId == null) return false;

        Category existingCategory = categoryDAO.getCategoryById(categoryId);
        if (existingCategory == null) {
            return false;
        }

        return categoryDAO.deleteCategory(categoryId);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        if (categoryId == null) return null;
        return categoryDAO.getCategoryById(categoryId);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) return null;
        return categoryDAO.getCategoryByName(categoryName.trim());
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public List<Category> getActiveCategories() {
        return categoryDAO.getActiveCategories();
    }
}