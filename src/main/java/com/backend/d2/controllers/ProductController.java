package com.backend.d2.controllers;

import com.backend.d2.dtos.products.request.ProductCreatedDTO;
import com.backend.d2.services.interfaces.ProductServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController{

    // inyeccion de Servicio
    private ProductServiceInterface productServiceInterface;

    // Metodo para agregar producto TASK-001
    @PostMapping
    public ResponseEntity<ProductCreatedDTO> createProduct (ProductCreatedDTO createdDTO){
        // Mappear de DTO a Modelo
        // Recordar excepciones en el global (Service se encarga de mandar excepciones)
        // Respuestas SOLO en DTO
        return null;
    }
}
