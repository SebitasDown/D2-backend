package com.backend.d2.controllers;

import com.backend.d2.dtos.category.CategoryRequest;
import com.backend.d2.dtos.category.CategoryResponseDTO;
import com.backend.d2.mappers.CategoryMapper;
import com.backend.d2.models.CategoryModel;
import com.backend.d2.services.interfaces.CategoryServiceInterface;
import com.backend.d2.services.interfaces.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    // inyeccion de Servicio
    private final CategoryServiceInterface categoryServiceInterface;

    // Metodo post agregar una categoria
    // SOLO para ADMIN
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory (@RequestBody CategoryRequest categoryRequest){
        // Mappeo de dto - entidad
        CategoryModel categoryModel = categoryServiceInterface.create(CategoryMapper.INSTANCE.dtoToModel(categoryRequest));

        // Mapeo de entidad - dto respuesta

        CategoryResponseDTO responseDTO = CategoryMapper.INSTANCE.modelToResponse(categoryModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Metodo para actualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request){
        CategoryModel categoryModel = CategoryMapper.INSTANCE.dtoToModel(request);
        categoryModel.setId(id);

        CategoryModel update = categoryServiceInterface.update(categoryModel);

        CategoryResponseDTO responseDTO = CategoryMapper.INSTANCE.modelToResponse(update);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // Metodo para eliminar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryServiceInterface.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message","Categoria eliminada correctamente");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
