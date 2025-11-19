package com.backend.d2.services.impl;

import com.backend.d2.models.ProductModel;
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface;
import com.backend.d2.services.interfaces.ProductServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ProductServiceImpl implements ProductServiceInterface {

    // Inyeccion de ProductRepositorio
    private ProductRepositoryInterface productRepository;
    // Inyeccion de Repositorio para Category y supplier

    @Override
    public ProductModel create(ProductModel productModel) {

        //Manejo de errores globales (BusinessException)
        if (productModel.getName() == null || productModel.getName().trim().isEmpty() || productModel.getBarcode() == null || productModel.getBarcode().trim().isEmpty() || productModel.getDescription() == null || productModel.getDescription().trim().isEmpty()){
            throw new IllegalArgumentException("Error momentaneo - Este error es para validar que no esten vacios estos campos"); // Recordar Poner las excepciones globales
        }
        if(productModel.getPrice() <0 || productModel.getStock()< 0){
            throw  new IllegalArgumentException("Error momentaneo");
        }
        if (productModel.getCategory() == null || productModel.getSupplier() == null){
            throw new IllegalArgumentException("Valor null"); // errores momentaneos
        }

        // Validacion de categoria
        Long categoryId = productModel.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orEleseThrow(() -> new IllegalArgumentException("Error momentaneo - Categoria no encontrada"));
        productModel.setCategory(category);

        // Validacion del proveedor
        Long supplierId = productModel.getSupplier().getId();
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Error Momentaneo- No se Encontro Ningun proveedor"));
        productModel.setSupplier(supplier);

        // Manda a repositorio a traves de la inyeccion
        return productRepository.save(productModel);
    }

    @Override
    public ProductModel updated(ProductModel productModel) {
        if (productModel.getName() == null || productModel.getName().trim().isEmpty() || productModel.getBarcode() == null || productModel.getBarcode().trim().isEmpty() || productModel.getDescription() == null || productModel.getDescription().trim().isEmpty()){
            throw new IllegalArgumentException("Error momentaneo - Este error es para validar que no esten vacios estos campos"); // Recordar Poner las excepciones globales
        }
        if(productModel.getPrice() <0 || productModel.getStock()< 0){
            throw  new IllegalArgumentException("Error momentaneo");
        }
        if (productModel.getCategory() == null || productModel.getSupplier() == null){
            throw new IllegalArgumentException("Valor null"); // errores momentaneos
        }
        if (productModel.getId() == null){
            throw new IllegalArgumentException("NOT NULL");
        }

        // Validacion si el producto existe
        ProductModel exist = productRepository.findById(productModel.getId())
                .orElseThrow(() -> new IllegalArgumentException("No encontrado")); // Error personalizado corregir

        // Validacion de categoria
        Long categoryId = productModel.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orEleseThrow(() -> new IllegalArgumentException("Error momentaneo - Categoria no encontrada"));
        productModel.setCategory(category);

        // Validacion del proveedor
        Long supplierId = productModel.getSupplier().getId();
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Error Momentaneo- No se Encontro Ningun proveedor"));
        productModel.setSupplier(supplier);

        exist.setCategory(category);
        exist.setSupplier(supplier);
        exist.setName(productModel.getName());
        exist.setBarcode(productModel.getBarcode());
        exist.setPrice(productModel.getPrice());
        exist.setStock(productModel.getStock());
        exist.setDescription(productModel.getDescription());
        return productRepository.update(exist);
    }

    @Override
    public ProductModel deleteById(Long id) {
        Optional<ProductModel> encontrado = productRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado"); // Error temporal
        }
        ProductModel product = encontrado.get();

        boolean eliminado = productRepository.deleteById(id);

        if (!eliminado) {
            throw new IllegalArgumentException("No se pudo eliminar el producto");
        }

        return product;
    }

    @Override
    public ProductModel findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"))
    }

    @Override
    public Page<ProductModel> findByName(String name, Pageable pageable) {
        Page<ProductModel> result = productRepository.findByName(name, pageable);
        if (result.isEmpty()){
            throw new IllegalArgumentException("No se encontro registro"); // Aqui recordar que van las excepciones globales
        }
        return result;
    }
}
