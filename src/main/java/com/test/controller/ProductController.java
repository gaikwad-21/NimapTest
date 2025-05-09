package com.test.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.test.model.Product;
import com.test.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	// Api for creating the product
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	// Api for getting All Products with Pagination
	@GetMapping
	public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "2") int size) {
		return productService.getPaginatedProducts(page, size);
	}

	// .Api for getting product byId
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id) {
		Optional<Product> productOptional = productService.getProductById(id);

		if (productOptional.isPresent()) {
			return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Api for updating the product
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product productDetails) {
		Product updatedProduct = productService.updateProduct(id, productDetails);
		if (updatedProduct == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedProduct);
	}

	// Api for product deletion
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id) {
		boolean deleted = productService.deleteProductById(id);
		if (deleted) {
			return ResponseEntity.ok("Product with ID " + id + " has been deleted."); // 204 No Content
		} else {
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}

}
