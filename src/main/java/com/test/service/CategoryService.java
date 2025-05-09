package com.test.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.test.model.Category;
import com.test.repository.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	CategoryRepo categoryRepo;

	// method for creating the category
	public Category createCategory(Category category) {
		return categoryRepo.save(category);
	}

	// method for getting all categories through pagination
	public Page<Category> getAllCategories(PageRequest pageRequest) {
		return categoryRepo.findAll(pageRequest);
	}

	// method for getting category byId
	public Optional<Category> getCategoryById(int id) {
		return categoryRepo.findById(id);
	}

	// Method for updating the category
	public Category updateCategory(int id, Category categoryDetails) {
		Optional<Category> existingCategoryOptional = categoryRepo.findById(id);

		if (existingCategoryOptional.isPresent()) {
			Category existingCategory = existingCategoryOptional.get();

			// Update the properties of the existing category
			existingCategory.setName(categoryDetails.getName()); // example property
			existingCategory.setDescription(categoryDetails.getDescription()); // example property

			// Save the updated category and return it
			return categoryRepo.save(existingCategory);
		}

		// If category doesn't exist, return null or handle as needed
		return null;
	}

	// method for deleting the category byId
	public boolean deleteCategory(int id) {
		Optional<Category> categoryOptional = categoryRepo.findById(id);
		if (categoryOptional.isPresent()) {
			categoryRepo.delete(categoryOptional.get());
			return true;
		}
		return false; // Category not found
	}

}
