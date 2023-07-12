package com.Arefin.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Arefin.entities.Category;
import com.Arefin.exceptions.ResouceNotFoundException;
import com.Arefin.payloads.CategoryDto;
import com.Arefin.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private com.Arefin.repositories.CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedcat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(addedcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
					   .orElseThrow(() -> new ResouceNotFoundException("Category" ,"Category ID " , categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		this.categoryRepo.save(cat);
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResouceNotFoundException("Category" ,"Category ID " , categoryId));
		this.categoryRepo.delete(cat);

	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResouceNotFoundException("Category" ,"Category ID " , categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCatogories() {
		List <Category> cat = this.categoryRepo.findAll();
		List <CategoryDto> catdto =cat.stream().map((c)->this.modelMapper.map(c, CategoryDto.class)).collect(Collectors.toList());
		return catdto;
	}

}
