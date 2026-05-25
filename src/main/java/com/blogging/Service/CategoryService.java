package com.blogging.Service;

import com.blogging.Exception.ResourceNotFoundException;
import com.blogging.Model.Category;
import com.blogging.Repository.CategoryRepository;
import com.blogging.dto.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryDto createCategory(CategoryDto categoryDto){
        Category category = categoryRepository.save(modelMapper.map(categoryDto,Category.class));
        return modelMapper.map(category,CategoryDto.class);
    }

    public CategoryDto getCategoryById(String id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return modelMapper.map(category,CategoryDto.class);
    }

    public List<CategoryDto> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category,CategoryDto.class)).toList();
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, String id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    public void deleteCategory(String id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}
