package com.BookStore.projectBookStore.controllers;

import com.BookStore.projectBookStore.entities.Pedido;
import com.BookStore.projectBookStore.entities.ReportDataDTO;
import com.BookStore.projectBookStore.repositories.PedidoRepository;
import com.BookStore.projectBookStore.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ReportService reportService;

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
    }    @GetMapping("/pedidos/create")
    public String createPedidoForm(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("title", "Crear Pedido");
        model.addAttribute("subtitle", "Formulario de nuevo pedido");
        return "pedido/create";
    }@PostMapping("/pedidos")
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

        try {
            // Guardar el pedido
            pedidoRepository.save(pedido);

            // Redirigir a la página de éxito con el mensaje
            model.addAttribute("message", "Elemento creado satisfactoriamente");
            return "pedido/success"; // Página de éxito
        } catch (Exception e) {
            // Log del error
            System.err.println("Error al guardar el pedido: " + e.getMessage());
            e.printStackTrace();
            
            // Devolver al formulario con mensaje de error
            model.addAttribute("error", "Error al guardar el pedido: " + e.getMessage());
            return "pedido/create";
        }
    }

    @PostMapping("/pedidos/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        pedidoRepository.deleteById(id);
        return "redirect:/pedidos";
    }

    @PostMapping("/pedidos/{id}/download")
    public ResponseEntity<byte[]> downloadPedidoReport(@PathVariable("id") Long id, @RequestParam String type) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        ReportDataDTO dto = mapPedidoToReportDataDTO(pedido);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            reportService.exportReportToStream(type, dto, baos);
            String filename = "pedido_" + id + ("pdf".equalsIgnoreCase(type) ? ".pdf" : ".xlsx");
            String contentType = "pdf".equalsIgnoreCase(type)
                    ? "application/pdf"
                    : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Método utilitario para mapear Pedido a ReportDataDTO
    private ReportDataDTO mapPedidoToReportDataDTO(Pedido pedido) {
        ReportDataDTO dto = new ReportDataDTO();
        dto.setPedidoId(pedido.getId());
        dto.setUsuarioPedido(pedido.getUsuario());
        dto.setPagoPedido(pedido.getPago());
        dto.setDescuentoPedido(pedido.isDescuento());
        dto.setItems(pedido.getItems());
        return dto;
    }
}

