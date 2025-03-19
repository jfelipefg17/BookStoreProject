package com.BookStore.projectBookStore.services;


import com.BookStore.projectBookStore.entities.Author;
import com.BookStore.projectBookStore.entities.Category;
import com.BookStore.projectBookStore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategory(int id, String name) {

        Category category = new Category(id, name);
        category.setId(id);
        category.setName(name);
        categoryRepository.save(category);
    }

    //Read-Search all categories
    public List<Category> searchAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    //Read-Search specific category by ID
    public Category findById(int id) throws Exception {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            return categoryOptional.get();

        } else {
            throw new Exception("Category not found with ID: " + id);
        }
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    //Update-Modify Category
    public void modifyCategory(int id, String name) throws Exception {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {

            Category category = new Category(id, name);
            category.setName(name);
            categoryRepository.save(category);

        } else {
            throw new Exception("Cannot modify. Category not found with ID: " + id);
        }
    }

    // Delete Category
    public void deleteCategory(Integer id) throws Exception {

        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);

        } else {
            throw new Exception("Cannot delete. Category not found with ID: " + id);
        }
    }
}
