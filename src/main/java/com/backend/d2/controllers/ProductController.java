package com.backend.d2.controllers;


import com.backend.d2.dtos.products.request.ProductCreatedDTO;
import com.backend.d2.dtos.products.responses.*;
import com.backend.d2.mappers.ProductMapper;
import com.backend.d2.mappers.SaleMapper;
import com.backend.d2.models.ProductModel;
import com.backend.d2.services.interfaces.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController{

    private SaleMapper saleMapper;

    // inyeccion de Servicio
    private ProductServiceInterface productServiceInterface;

    // Metodo para agregar producto TASK-001
    @PostMapping("/")
    public ResponseEntity<ProductCreateResponseDTO> createProduct (@RequestBody ProductCreatedDTO createdDTO){
        // Mappear de DTO a Modelo
        ProductModel productModel = productServiceInterface.create(ProductMapper.INSTANCE.toModel(createdDTO));
        // Mappeo de respuesta
        ProductCreateResponseDTO response = ProductMapper.INSTANCE.toResponseCreateDTO(productModel);
        // Recordar excepciones en el global (Service se encarga de mandar excepciones)
        // Respuestas SOLO en DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Metdo para actualizar producto Con ROl (ADMIN)
    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponseDTO> updateProduct (@PathVariable Long id, @RequestBody ProductCreatedDTO updatedDTO){
      ProductModel productModel = ProductMapper.INSTANCE.toModel(updatedDTO);
      productModel.setId(id);

      ProductModel updated = productServiceInterface.updated(productModel);

      ProductUpdateResponseDTO res = ProductMapper.INSTANCE.toResponseUpdatedDTO(updated);

      return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //Metodo para Eliminar un producto
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDeleteDTO> deleteProduct (@PathVariable Long id){
        ProductModel productModel = productServiceInterface.deleteById(id);

        ProductResponseDeleteDTO response = new ProductResponseDeleteDTO(productModel.getName(),"producto eliminado");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Metodo para Buscar por ID
    // Acceso por rol Con Usuarios (Spring Segurity)
    @GetMapping("/{id}")
    public ResponseEntity<ProductGetByIdResponseDTO> getProduct (@PathVariable Long id){
        ProductModel model = productServiceInterface.findById(id);

        ProductGetByIdResponseDTO dto = ProductMapper.INSTANCE.toGetDTO(model);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<ProductSearchResponseDTO>> searchProduct(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<ProductModel> found = productServiceInterface.findByName(name, pageable);

        Page<ProductSearchResponseDTO> response =
                found.map(ProductMapper.INSTANCE::toSearchDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
}
