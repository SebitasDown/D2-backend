package com.backend.d2.controllers;

import com.backend.d2.dtos.category.CategoryRequest;
import com.backend.d2.dtos.category.CategoryResponseDTO;
import com.backend.d2.mappers.CategoryMapper;
import com.backend.d2.models.CategoryModel;
import com.backend.d2.services.interfaces.CategoryServiceInterface;
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

    // Metodo con Pageable y filtro dinamico
    @GetMapping("/list")
    public ResponseEntity<Page<CategoryResponseDTO>> list (
            @RequestParam(required = false) String name,
            Pageable pageable
    ){
        Page<CategoryModel> result = categoryServiceInterface.findAll(name, pageable);
        Page<CategoryResponseDTO> response = result.map(CategoryMapper.INSTANCE::modelToResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Lista de categorias
    @GetMapping("/listAll")
    public ResponseEntity<Page<CategoryResponseDTO>> listCategory (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "name") String sortBy
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<CategoryModel> pageModel = categoryServiceInterface.listAll(pageable);

        Page<CategoryResponseDTO> pageResponseDTO = pageModel.map(CategoryMapper.INSTANCE::modelToResponse);
        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDTO);
    }

}
