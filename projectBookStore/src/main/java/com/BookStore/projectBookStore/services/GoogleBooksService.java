package com.BookStore.projectBookStore.services;

import com.BookStore.projectBookStore.dto.GoogleBookDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public List<GoogleBookDTO> searchBooks(String query) {
        String url = UriComponentsBuilder
                .fromUriString("https://www.googleapis.com/books/v1/volumes")
                .queryParam("q", query)
                .queryParam("maxResults", 10)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
        List<GoogleBookDTO> books = new ArrayList<>();

        if (items != null) {
            for (Map<String, Object> item : items) {
                Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");

                GoogleBookDTO book = new GoogleBookDTO();
                book.setTitle((String) volumeInfo.get("title"));
                book.setAuthors((List<String>) volumeInfo.get("authors"));
                book.setPublisher((String) volumeInfo.get("publisher"));
                book.setPublishedDate((String) volumeInfo.get("publishedDate"));
                book.setDescription((String) volumeInfo.get("description"));

                Map<String, Object> imageLinks = (Map<String, Object>) volumeInfo.get("imageLinks");
                if (imageLinks != null && imageLinks.get("thumbnail") != null) {
                    book.setThumbnail((String) imageLinks.get("thumbnail"));
                }

                books.add(book);
            }
        }

        return books;
    }
}


