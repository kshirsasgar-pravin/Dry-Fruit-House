package com.tca.test;

import java.util.List;

import com.tca.dao.impl.CategoryDAOImpl;
import com.tca.entity.Category;

public class CategoryDAOTest {
	CategoryDAOImpl categoryDAO = new CategoryDAOImpl();

	public void saveCategoryTest() {
		Category category = new Category();

		category.setCategoryName("Dry Fruits");
		category.setDescription("Premium quality dry fruits and nuts.");
		category.setIsActive(true);

		if (categoryDAO.saveCategory(category)) {
			System.out.println("Category Saved Successfully");
		} else {
			System.out.println("Failed to Save Category");
		}

	}

	public void updateCategoryTest(Long categoryId) {

	    Category category = categoryDAO.getCategoryById(categoryId);

	    if (category == null) {
	        System.out.println("Category Not Found");
	        return;
	    }

	    category.setCategoryName("Premium Dry Fruits");
	    category.setDescription("Premium quality imported dry fruits.");
	    category.setIsActive(true);

	    if (categoryDAO.updateCategory(category)) {
	        System.out.println("Category Updated Successfully");
	    } else {
	        System.out.println("Failed to Update Category");
	    }
	}
	
	public void getCategoryById(Long categoryId) {
		Category category = categoryDAO.getCategoryById(categoryId);
		System.out.println("Category by Id "+category);
	}
	
	public void getCategoryByName(String name) {
		Category category = categoryDAO.getCategoryByName(name);
		System.out.println("Category by Name "+category);
	}
	
	public void getAllCagegory() {
		List<Category> categoryList= categoryDAO.getAllCategories();
		System.out.println("category by list "+categoryList);
	}
	
	public void getActiveCategories() {
		System.out.println("Active Categoryies "+categoryDAO.getActiveCategories());
	}
	
	public void deleteCategoryTest(Long categoryId) {
		if(categoryDAO.deleteCategory(categoryId)) {
			System.out.println("Category is deleted Successfully");
			
		}
		else System.out.println("Faild to delete the category ");
		
	}
}
