package com.docencia.tutorial.controllers;

import com.docencia.tutorial.models.Pedido;
import com.docencia.tutorial.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/pedidos")
    public String index(Model model) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        model.addAttribute("title", "Pedidos - Sistema de Ventas");
        model.addAttribute("subtitle", "Lista de pedidos");
        model.addAttribute("pedidos", pedidos);
        return "pedido/index"; // Retorna la vista pedido/index.html (Thymeleaf)
    }

    @GetMapping("/pedidos/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        model.addAttribute("title", "Pedido #" + id);
        model.addAttribute("subtitle", "Detalles del pedido");
        model.addAttribute("pedido", pedido);
        return "pedido/show"; // Retorna la vista pedido/show.html (Thymeleaf)
    }

    @GetMapping("/pedidos/create")
    public String createPedidoForm(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "pedido/create";
    }

    @PostMapping("/pedidos")
    public String save(@ModelAttribute Pedido pedido,
                       @RequestParam(value = "descuento", required = false) String descuento,
                       Model model) {
        // Establecer si el pedido tiene descuento
        pedido.setDescuento(descuento != null);

        // Validaciones
        if (pedido.getUsuario() == null || pedido.getUsuario().isEmpty() ||
                pedido.getItems() == null || pedido.getItems().isEmpty() ||
                pedido.getPago() == null) {
            model.addAttribute("error", "Usuario, Items y Pago son obligatorios");
            return "pedido/create"; // Volver al formulario con el mensaje de error
        }

        // Guardar el pedido
        pedidoRepository.save(pedido);

        // Redirigir a la página de éxito con el mensaje
        model.addAttribute("message", "Elemento creado satisfactoriamente");
        return "pedido/success"; // Página de éxito
    }

    @PostMapping("/pedidos/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        pedidoRepository.deleteById(id);
        return "redirect:/pedidos";
    }
}

