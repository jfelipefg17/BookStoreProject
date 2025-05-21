package com.BookStore.projectBookStore.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Obtener el código de error
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        
        // Obtener el mensaje de error
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        
        // Obtener la excepción
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        
        // Agregar información al modelo
        model.addAttribute("statusCode", statusCode != null ? statusCode : "Desconocido");
        model.addAttribute("errorMessage", errorMessage != null ? errorMessage : "Error desconocido");
        
        if (throwable != null) {
            model.addAttribute("exceptionMessage", throwable.getMessage());
        }
        
        // Devolver la vista de error personalizada
        return "error";
    }
}
