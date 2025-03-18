package com.BookStore.projectBookStore.services;


import com.BookStore.projectBookStore.entities.Author;
import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public void createAuthor(int id, String name) {

        Author author = new Author();
        author.setId(id);
        author.setName(name);
        authorRepository.save(author);
    }

    //Read-Search all authors
    public List<Author> searchAllBook() {
        return authorRepository.findAll();
    }

    //Read-Search specific author by ID
    public Author findById(int id) throws Exception {

        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            return authorOptional.get();

        } else {
            throw new Exception("Author not found with ID: " + id);
        }
    }

    //Update-Modify Author
    public void modifyBook(int id, String name) throws Exception {

        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {

            Author author = authorOptional.get();
            author.setName(name);
            authorRepository.save(author);

        } else {
            throw new Exception("Cannot modify. Author not found with ID: " + id);
        }
    }

    // Delete Author
    public void deleteBook(Integer id) throws Exception {

        if (authorRepository.findById(id).isPresent()) {
            authorRepository.deleteById(id);

        } else {
            throw new Exception("Cannot delete. Author not found with ID: " + id);
        }
    }
}
