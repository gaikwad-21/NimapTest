package com.test.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.test.model.Category;
import com.test.model.Product;
import com.test.repository.CategoryRepo;
import com.test.repository.ProductRepo;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;

	@Autowired
	CategoryRepo categoryRepo;

	// method of creating the project
	public Product createProduct(Product product) {
		// Find category by ID and set it to the product
		Category category = categoryRepo.findById(product.getCategory().getId())
				.orElseThrow(() -> new RuntimeException("Category not found"));
		product.setCategory(category); // Set category for the product

		// Save product to the database
		return productRepo.save(product);
	}

	// method of getting all the products through pagination
	public Page<Product> getPaginatedProducts(int page, int size) {
		return productRepo.findAll(PageRequest.of(page, size));
	}

	// method for getting product byId
	public Optional<Product> getProductById(int id) {
		return productRepo.findById(id);
	}

	// method for updating the product
	public Product updateProduct(int id, Product productDetails) {
		Optional<Product> optionalProduct = productRepo.findById(id);
		if (!optionalProduct.isPresent()) {
			return null;
		}

		Product existingProduct = optionalProduct.get();
		existingProduct.setProductName(productDetails.getProductName());
		existingProduct.setPrice(productDetails.getPrice());
		existingProduct.setQuantity(productDetails.getQuantity());

		// If category is provided, fetch and set it
		if (productDetails.getCategory() != null && productDetails.getCategory().getId() > 0) {
			Category category = categoryRepo.findById(productDetails.getCategory().getId()).orElse(null);
			if (category != null) {
				existingProduct.setCategory(category);
			}
		}

		return productRepo.save(existingProduct);
	}

	// Method For deleting the product
	public boolean deleteProductById(int id) {
		if (productRepo.existsById(id)) {
			productRepo.deleteById(id);
			return true;
		}
		return false;
	}
}
