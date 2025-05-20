package com.BookStore.projectBookStore.repositories;

import com.BookStore.projectBookStore.entities.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}
