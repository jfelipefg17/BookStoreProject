package com.BookStore.projectBookStore.repositories;


import com.BookStore.projectBookStore.entities.Author;
import com.BookStore.projectBookStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Find-search all categories
    List<Category> findAll();

    // Find-search category by id
    Optional<Category> findById(Integer id);

    // Save-Update category
    Category save(Category category);

    // Delete category
    void deleteById(Integer id);

    Optional<Category> findByName(String name);
}
