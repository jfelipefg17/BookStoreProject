-- Crear tabla de autores
CREATE TABLE authors (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL
);

-- Crear tabla de categorías
CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

-- Crear tabla de editoriales
CREATE TABLE publishers (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

-- Crear tabla de libros
CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       stock INT NOT NULL,
                       price DECIMAL(10,2) NOT NULL,
                       image VARCHAR(255),
                       author_id INT,
                       publisher_id INT,
                       category_id INT,
                       FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE SET NULL,
                       FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE SET NULL,
                       FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Crear tabla de reseñas
CREATE TABLE reviews (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT NOT NULL,
                         book_id INT NOT NULL,
                         FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- Crear tabla de likes
CREATE TABLE likes (
                       id SERIAL PRIMARY KEY,
                       user_id INT NULL,
                       book_id INT NULL,
                       review_id INT NULL,
                       date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
                       FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE
);
