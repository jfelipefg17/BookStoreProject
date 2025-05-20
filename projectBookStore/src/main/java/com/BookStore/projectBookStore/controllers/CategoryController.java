package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.Category;
import com.BookStore.projectBookStore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.searchAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id) throws Exception {
        return categoryService.findById(id);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) throws Exception {
        return categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) throws Exception {
        category.setId(id);
        return categoryService.save(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) throws Exception {
        categoryService.deleteById(id);
    }
}
