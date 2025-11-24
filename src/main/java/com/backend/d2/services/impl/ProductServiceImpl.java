package com.backend.d2.services.impl;

import com.backend.d2.exceptions.BusinessException;
import com.backend.d2.models.CategoryModel;
import com.backend.d2.models.ProductModel;
import com.backend.d2.models.SupplierModel;
import com.backend.d2.repositories.interfaces.CategoryRepositoryInterface;
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface;
import com.backend.d2.repositories.interfaces.SupplierRepositoryInterface;
import com.backend.d2.services.interfaces.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductServiceInterface {

    private final ProductRepositoryInterface productRepository;
    private final CategoryRepositoryInterface categoryRepository;
    private final SupplierRepositoryInterface supplierRepository;

    @Override
    public ProductModel create(ProductModel productModel) {

        // Validaciones básicas de campos vacíos
        if (productModel.getName() == null || productModel.getName().trim().isEmpty() ||
                productModel.getBarcode() == null || productModel.getBarcode().trim().isEmpty() ||
                productModel.getDescription() == null || productModel.getDescription().trim().isEmpty()){
            throw new BusinessException("BAD_REQUEST", "Los campos obligatorios no pueden estar vacíos");
        }

        // price < 0 se convierte en price.compareTo(BigDecimal.ZERO) < 0
        if (productModel.getPrice() != null && productModel.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("BAD_REQUEST", "El precio no puede ser negativo");
        }
        if (productModel.getStock() < 0) {
            throw new BusinessException("BAD_REQUEST", "El stock no puede ser negativo");
        }

        if (productModel.getCategory() == null || productModel.getSupplier() == null){
            throw new BusinessException("BAD_REQUEST", "La categoría y el proveedor son obligatorios");
        }

        // Validación de categoria
        Long categoryId = productModel.getCategory().getId();
        CategoryModel category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException("NOT_FOUND", "No se encontró la categoría con ID: " + categoryId));
        productModel.setCategory(category);

        // Validación del proveedor
        Long supplierId = productModel.getSupplier().getId();
        SupplierModel supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new BusinessException("NOT_FOUND", "No se encontró el proveedor con ID: " + supplierId));
        productModel.setSupplier(supplier);

        return productRepository.save(productModel);
    }

    @Override
    public ProductModel updated(ProductModel productModel) {
        // Validaciones básicas
        if (productModel.getId() == null){
            throw new BusinessException("BAD_REQUEST", "El ID del producto es necesario para actualizar");
        }
        if (productModel.getName() == null || productModel.getName().trim().isEmpty() ||
                productModel.getBarcode() == null || productModel.getBarcode().trim().isEmpty()){
            throw new BusinessException("BAD_REQUEST", "No se permiten campos vacíos");
        }

        if (productModel.getPrice() != null && productModel.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("BAD_REQUEST", "No se permiten precios negativos");
        }
        if (productModel.getStock() < 0) {
            throw new BusinessException("BAD_REQUEST", "No se permite stock negativo");
        }

        // Buscar producto existente
        ProductModel exist = productRepository.findById(productModel.getId())
                .orElseThrow(() -> new BusinessException("NOT_FOUND", "Producto no encontrado"));

        // Validar y actualizar categoría
        if (productModel.getCategory() != null && productModel.getCategory().getId() != null) {
            CategoryModel category = categoryRepository.findById(productModel.getCategory().getId())
                    .orElseThrow(() -> new BusinessException("NOT_FOUND", "Categoría no encontrada"));
            exist.setCategory(category);
        }

        // Validar y actualizar proveedor
        if (productModel.getSupplier() != null && productModel.getSupplier().getId() != null) {
            SupplierModel supplier = supplierRepository.findById(productModel.getSupplier().getId())
                    .orElseThrow(() -> new BusinessException("NOT_FOUND", "Proveedor no encontrado"));
            exist.setSupplier(supplier);
        }

        // Actualizar campos
        exist.setName(productModel.getName());
        exist.setBarcode(productModel.getBarcode());

        // Aquí no hay problema porque ambos son BigDecimal
        exist.setPrice(productModel.getPrice());

        exist.setStock(productModel.getStock());
        exist.setDescription(productModel.getDescription());

        return productRepository.update(exist);
    }

    @Override
    public ProductModel deleteById(Long id) {
        Optional<ProductModel> encontrado = productRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new BusinessException("NOT_FOUND", "Producto no encontrado");
        }
        ProductModel product = encontrado.get();

        boolean eliminado = productRepository.deleteById(id);

        if (!eliminado) {
            throw new BusinessException("INTERNAL_ERROR", "No se pudo eliminar el producto");
        }

        return product;
    }

    @Override
    public ProductModel findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("NOT_FOUND", "Producto no encontrado"));
    }

    @Override
    public Page<ProductModel> findByName(String name, Pageable pageable) {
        Page<ProductModel> result = productRepository.findByName(name, pageable);
        if (result.isEmpty()){
            throw new BusinessException("NOT_FOUND", "No se encontraron productos con ese nombre");
        }
        return result;
    }

    @Override
    public Page<ProductModel> listAll(String name, Long categoryId, Long supplierId, Pageable pageable) {
        return productRepository.findByFilters(name, categoryId, supplierId, pageable);
    }

    // Este metodo es auxiliar, no está en la interfaz, pero lo corregimos por si acaso
    public ProductModel checkStockAndUpdate(Long productId, int cantidad){
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(()-> new BusinessException("NOT_FOUND", "Producto no encontrado para validar stock"));

        if (product.getStock() < cantidad){
            throw new BusinessException("BAD_REQUEST", "No hay suficiente stock. Disponible: " + product.getStock());
        }
        if(product.getStock() == 0) {
            throw new BusinessException("BAD_REQUEST", "Producto agotado");
        }

        int nuevoStock = product.getStock() - cantidad;
        product.setStock(nuevoStock);

        if (nuevoStock == 0) {
            // Podrías lanzar una advertencia o simplemente dejar pasar
        } else if (nuevoStock < 10) {
            // Stock bajo warning
        }

        productRepository.save(product);
        return product;
    }
}