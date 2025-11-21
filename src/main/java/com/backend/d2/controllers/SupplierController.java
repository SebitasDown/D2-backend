package com.backend.d2.controllers;

import com.backend.d2.dtos.supplier.request.SupplierCreateDTO;
import com.backend.d2.dtos.supplier.response.SupplierResponseDTO;
import com.backend.d2.mappers.SupplierMapper;
import com.backend.d2.models.SupplierModel;
import com.backend.d2.services.interfaces.SupplierServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {

    // Inyeccion de service para traer metodos
    private final SupplierServiceInterface supplierServiceInterface;

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> create(@RequestBody SupplierCreateDTO supplierCreateDTO){
        SupplierModel model = supplierServiceInterface.create(SupplierMapper.INSTANCE.toModelCreate(supplierCreateDTO));
        SupplierResponseDTO responseDTO = SupplierMapper.INSTANCE.toDtoCreateResponse(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{d}")
    public ResponseEntity<SupplierResponseDTO> update (@PathVariable Long id, @RequestBody SupplierCreateDTO supplierCreateDTO){
        SupplierModel model = supplierServiceInterface.update(SupplierMapper.INSTANCE.toModelCreate(supplierCreateDTO));
        SupplierResponseDTO responseDTO = SupplierMapper.INSTANCE.toDtoCreateResponse(model);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        supplierServiceInterface.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> search (@RequestParam String name){
        return ResponseEntity.status(HttpStatus.OK).body(supplierServiceInterface.searchByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> findByIds (@PathVariable Long id){
        SupplierModel supplierModel = supplierServiceInterface.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(SupplierMapper.INSTANCE.toDtoCreateResponse(supplierModel));
    }

    @GetMapping
    public ResponseEntity<Page<SupplierResponseDTO>> listAll (Pageable pageable){
        Page<SupplierModel> supplierModels = supplierServiceInterface.listAll(pageable);

        Page<SupplierResponseDTO> responseDTOS = supplierModels
                .map(SupplierMapper.INSTANCE::toDtoCreateResponse);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOS);
    }

}
