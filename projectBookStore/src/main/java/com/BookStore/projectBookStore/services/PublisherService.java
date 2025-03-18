package com.BookStore.projectBookStore.services;


import com.BookStore.projectBookStore.entities.Book;
import com.BookStore.projectBookStore.entities.Publisher;
import com.BookStore.projectBookStore.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public void createPublisher(int id, String name) {

        Publisher publisher = new Publisher(id, name);
        publisher.setId(id);
        publisher.setName(name);
        publisherRepository.save(publisher);
    }

    //Read-Search all publishers
    public List<Publisher> searchAllPublisher() {
        return publisherRepository.findAll();
    }

    //Read-Search specific publisher by ID
    public Publisher findById(int id) throws Exception {

        Optional<Publisher> publisherOptional = publisherRepository.findById(id);

        if (publisherOptional.isPresent()) {
            return publisherOptional.get();

        } else {
            throw new Exception("Publisher not found with ID: " + id);
        }
    }

    //Update-Modify Book
    public void modifyPublisher(int id, String name) throws Exception {

        Optional<Publisher> publisherOptional = publisherRepository.findById(id);

        if (publisherOptional.isPresent()) {

            Publisher publisher = publisherOptional.get();
            publisher.setName(name);
            publisherRepository.save(publisher);

        } else {
            throw new Exception("Cannot modify. Publisher not found with ID: " + id);
        }
    }

    // Delete Book
    public void deleteBook(Integer id) throws Exception {

        if (publisherRepository.existsById(id)) {
            publisherRepository.deleteById(id);

        } else {
            throw new Exception("Cannot delete. Author not found with ID: " + id);
        }
    }
}
