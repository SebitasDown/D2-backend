package com.backend.d2.controllers;

import com.backend.d2.dtos.products.request.ProductCreatedDTO;
import com.backend.d2.dtos.products.responses.*;
import com.backend.d2.mappers.ProductMapper;
import com.backend.d2.models.ProductModel;
import com.backend.d2.services.interfaces.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    // final es obligatorio para que @RequiredArgsConstructor inyecte el servicio
    private final ProductServiceInterface productServiceInterface;

    // Inyectamos el Mapper
    private final ProductMapper productMapper;

    // Metodo para agregar producto TASK-001
    @PostMapping
    public ResponseEntity<ProductCreateResponseDTO> createProduct(@RequestBody ProductCreatedDTO createdDTO) {
        // Mapear de DTO a Modelo usando la instancia inyectada
        ProductModel productModel = productServiceInterface.create(productMapper.toModel(createdDTO));

        // Mapeo de respuesta
        ProductCreateResponseDTO response = productMapper.toResponseCreateDTO(productModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Metodo para actualizar producto Con Rol (ADMIN)
    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductCreatedDTO updatedDTO) {
        ProductModel productModel = productMapper.toModel(updatedDTO);
        productModel.setId(id);

        ProductModel updated = productServiceInterface.updated(productModel);

        ProductUpdateResponseDTO res = productMapper.toResponseUpdatedDTO(updated);

        return ResponseEntity.ok(res);
    }

    // Metodo para Eliminar un producto
    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDeleteDTO> deleteProduct(@PathVariable Long id) {
        ProductModel productModel = productServiceInterface.deleteById(id);

        // Asumo que tu DTO tiene este constructor según tu código anterior
        ProductResponseDeleteDTO response = new ProductResponseDeleteDTO(
                productModel.getName(),
                "Producto eliminado exitosamente"
        );

        return ResponseEntity.ok(response);
    }

    // Metodo para Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductGetByIdResponseDTO> getProduct(@PathVariable Long id) {
        ProductModel model = productServiceInterface.findById(id);
        ProductGetByIdResponseDTO dto = productMapper.toGetDTO(model);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductSearchResponseDTO>> searchProduct(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy // 'id' o 'name' son mejores defaults que 'name' repetido
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<ProductModel> found = productServiceInterface.findByName(name, pageable);

        // Usamos referencia al método de la instancia inyectada
        Page<ProductSearchResponseDTO> response = found.map(productMapper::toSearchDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductSearchResponseDTO>> listProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<ProductModel> pageModel = productServiceInterface.listAll(name, categoryId, supplierId, pageable);

        Page<ProductSearchResponseDTO> pageResponseDTO = pageModel.map(productMapper::toSearchDTO);

        return ResponseEntity.ok(pageResponseDTO);
    }
}