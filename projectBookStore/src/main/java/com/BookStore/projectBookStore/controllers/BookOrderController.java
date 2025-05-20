package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.BookOrder;
import com.BookStore.projectBookStore.repositories.BookOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orders")
public class BookOrderController {

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @PostMapping("/create")
    public String createOrder(@RequestParam Long bookId, @RequestParam String client, RedirectAttributes redirectAttributes) {
        BookOrder order = new BookOrder();
        order.setClient(client);
        // Si quieres guardar el bookId, agrega un campo en BookOrder y setéalo aquí
        bookOrderRepository.save(order);
        redirectAttributes.addFlashAttribute("success", "¡Orden creada exitosamente!");
        return "redirect:/book/listBooks";
    }
}
