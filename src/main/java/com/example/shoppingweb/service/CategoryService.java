package com.example.shoppingweb.service;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void deleteCategoryById(int id){
        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(int id) throws UserNotFoundException {
        Optional <Category> result = categoryRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("Could not find any user with id" + id);
    }
}
