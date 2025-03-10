package com.category.rest;

import java.util.ArrayList;
import java.util.List;

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

import com.category.entity.Category;
import com.category.exception.ResourceNotFoundException;
import com.category.service.CategoryService;

@RestController
@RequestMapping("/api2")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@PostMapping("/category")
	public ResponseEntity<String> saveCategory(@RequestBody Category category){
		String message = service.saveCategory(category);
		return new ResponseEntity<>(message,HttpStatus.CREATED);
	}
	
	@PostMapping("/category/children/{parentId}")
	public ResponseEntity<String> saveCategoryWithChildren(@PathVariable Integer parentId,
			@RequestBody List<Category> category) throws ResourceNotFoundException{
		String message = service.saveCategoryWithChildren(parentId, category);
		return new ResponseEntity<>(message,HttpStatus.CREATED);
	}
	
	@PutMapping("/category/update/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable Integer id,
			@RequestBody Category category) throws ResourceNotFoundException{
		 String message = service.updateCategory(id, category);
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@GetMapping("/category/all")
	public ResponseEntity<List<String>> getCategories(){
		List<String> categories = service.getCategories();
		return new ResponseEntity<List<String>>(categories,HttpStatus.OK);
	}
	
	@GetMapping("/category/id/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable Integer id) throws ResourceNotFoundException{
		Category category = service.getCategory(id);
		return new ResponseEntity<Category>(category,HttpStatus.OK);
	}
	
	@GetMapping("/category/unique/{name}/{alias}")
	public ResponseEntity<String> checkUnique(@PathVariable String name,@PathVariable String alias){
		String message = service.checkUnique(name, alias);
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@DeleteMapping("/category/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer id) throws ResourceNotFoundException{
		String message = service.deleteCategory(id);
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@PostMapping("/category/enabled/{id}/{status}")
	public ResponseEntity<String> changeStatus(@PathVariable Integer id,@PathVariable boolean status){
		boolean changed = service.changeStatus(id, status);
		if(changed) {
			return new ResponseEntity<String>("Category Status Changed",HttpStatus.OK);
		}
		return null;
	}
}
