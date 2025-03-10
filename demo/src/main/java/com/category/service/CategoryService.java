package com.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.category.entity.Category;
import com.category.exception.ResourceNotFoundException;
import com.category.repo.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo repo;

	public String saveCategory(Category category) {
		Category saved = repo.save(category);
		if(saved.getId()>0) {
			return "Category Saved Succesfully";
		}
		return "Category Not Saved Succesfully";
	}

	public String saveCategoryWithChildren(Integer parentId,List<Category> categories) throws ResourceNotFoundException {
		Category parent = repo.findById(parentId).orElseThrow(()-> 
		new ResourceNotFoundException("Category Not Found With Id :"+parentId));

		for(Category category:categories) {
			category.setParent(parent);
		}
		repo.saveAll(categories);
		return "Category Saved Succesfully";
	}


	public List<String> getCategories(){
		ArrayList<String> list = new ArrayList<>();
		List<Category> categories = repo.findAll();
		for(Category category:categories) {
			if(category.getParent()==null) {
				list.add(category.getName());
				Set<Category> children = category.getChildren();
				for(Category subCategory:children) {
					String str="--"+subCategory.getName();
					list.add(str);
					getSubCategories(list, subCategory, 1);
				}
			}
		}
		return list;
	}


	private static void getSubCategories(List<String> hierarchicalNames,Category subCategory,int subLevel){
		int newSubLevel=subLevel+1;

		Set<Category> children = subCategory.getChildren();
		String str="";
		for(Category subCategory2:children) {

			for(int i=0;i<newSubLevel;i++) {
				str=str+"--";
			}
			str=str+subCategory2.getName();
			hierarchicalNames.add(str);
			getSubCategories(hierarchicalNames, subCategory2, newSubLevel);
		}
	}
	
	public Category getCategory(Integer id) throws ResourceNotFoundException {
		Optional<Category> findById = repo.findById(id);
		if(findById.isPresent()) {
			Category category = findById.get();
			return category;
		}else {
			throw new ResourceNotFoundException("Category Not Exist with Id : "+id);
		}
	}
	
	public String updateCategory(Integer id,Category category) throws ResourceNotFoundException {
		Optional<Category> findById = repo.findById(id);
		if(findById.isPresent()) {
			Category cat= findById.get();
			cat.setAlias(category.getAlias());
			cat.setEnabled(category.isEnabled());
			cat.setName(category.getName());
			repo.save(cat);
			return "Category Updated Succesfully";
		}else {
			throw new ResourceNotFoundException("Category Not Exist with Id : "+id);
		}
	}
	
	public String checkUnique(String name,String alias) {
		Category findByName = repo.findByName(name);
		if(findByName!=null) {
			return "Category Already Exist With Name : "+name;
		}
		Category findByAlias = repo.findByAlias(alias);
		if(findByAlias!=null) {
			return "Category Already Exist With Alias : "+alias;
		}
		return "Unique Category";
	}
	
	public String deleteCategory(Integer id) throws ResourceNotFoundException {
		Optional<Category> findById = repo.findById(id);
		if(findById.isPresent()) {
			repo.deleteById(id);
			return "Category Deleted Succesfully";
		}
		throw new ResourceNotFoundException("Category Not Exist with Id : "+id);
	}
	
	public boolean changeStatus(Integer id,boolean status) {
		Optional<Category> findById = repo.findById(id);
		if(findById.isPresent()) {
			Category category = findById.get();
			category.setEnabled(status);
			repo.save(category);
			return true;
		}
		return false;
	}
	
	

}
