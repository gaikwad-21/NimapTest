package com.test.controller;

import org.springframework.data.domain.Page;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.Category;
import com.test.model.Product;
import com.test.service.CategoryService;
import com.test.service.ProductService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// Api For creating the category
	@PostMapping
	public Category createCategory(@RequestBody Category category) {
		return categoryService.createCategory(category);
	}

	// Api for getting all the categories
	@GetMapping
	public Page<Category> getAllCategories(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		return categoryService.getAllCategories(PageRequest.of(page, size));
	}

	// Api for getting category byId
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
		Optional<Category> categoryOptional = categoryService.getCategoryById(id);

		if (categoryOptional.isPresent()) {
			return new ResponseEntity<>(categoryOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Api for updating the category
	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category categoryDetails) {
		Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
		if (updatedCategory == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedCategory);
	}

	// Api for deleting the category
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable int id) {
		boolean isDeleted = categoryService.deleteCategory(id);
		if (isDeleted) {
			return ResponseEntity.ok("Category with ID " + id + " has been deleted.");
		} else {
			return ResponseEntity.notFound().build(); // HTTP 404: Not Found if category doesn't exist
		}
	}

}
