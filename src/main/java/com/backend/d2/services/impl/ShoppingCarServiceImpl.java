package com.backend.d2.services.impl;

import com.backend.d2.exceptions.BadRequestException;
import com.backend.d2.exceptions.ResourceNotFoundException;
import com.backend.d2.models.ProductModel;
import com.backend.d2.models.ShoppingCarModel;
import com.backend.d2.repositories.interfaces.ISaleRepository; // Interfaz limpia
import com.backend.d2.repositories.interfaces.IShoppingCarRepository; // Interfaz limpia
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface; // Interfaz limpia
import com.backend.d2.services.interfaces.IShoppingCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCarServiceImpl implements IShoppingCarService {

    // INYECCIÓN DE DEPENDENCIAS (Arquitectura Limpia)
    private final IShoppingCarRepository shoppingCarRepository;
    private final ProductRepositoryInterface productRepository;
    private final ISaleRepository saleRepository;

    // Task 001: Agregar item (Lógica con Modelos)
    @Override
    @Transactional
    public ShoppingCarModel addItem(Long cashierId, Long productId, Integer quantity) {

        // Validar existencia del producto (Modelo)
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Validar stock disponible
        if (product.getStock() < quantity) {
            throw new BadRequestException("Not enough stock available. Current: " + product.getStock());
        }

        // Verificar si ya existe item en carrito activo
        Optional<ShoppingCarModel> optionalItem =
                shoppingCarRepository.findActiveItemByCashierAndProduct(cashierId, productId);

        ShoppingCarModel item;

        if (optionalItem.isPresent()) {
            // Ya existe -> incrementar cantidad
            item = optionalItem.get();
            int newQuantity = item.getQuantity() + quantity;

            if (newQuantity > product.getStock()) {
                throw new BadRequestException("Not enough stock to increase quantity");
            }

            item.setQuantity(newQuantity);
            // Recalcular subtotal
            item.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(newQuantity)));

        } else {
            // No existe -> crear uno nuevo (Modelo)
            item = new ShoppingCarModel();
            item.setCashierId(cashierId); // ID del usuario
            item.setProductId(product.getId()); // ID del producto
            item.setQuantity(quantity);
            item.setPrice(product.getPrice()); // BigDecimal directo del modelo
            item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            item.setSaleId(null); // Aún no tiene venta asignada
        }

        // Guardar usando el repositorio que acepta Modelos
        return shoppingCarRepository.save(item);
    }

    // ============================================================
    // Task 002: Actualizar cantidad
    // ============================================================
    @Override
    @Transactional
    public ShoppingCarModel updateItem(Long itemId, Integer quantity) {

        ShoppingCarModel item = shoppingCarRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        ProductModel product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (quantity > product.getStock()) {
            throw new BadRequestException("Not enough stock");
        }

        item.setQuantity(quantity);
        item.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(quantity)));

        return shoppingCarRepository.save(item);
    }

    // Task 003: Eliminar item
    @Override
    @Transactional
    public boolean deleteItem(Long itemId) {
        if (!shoppingCarRepository.existsById(itemId)) {
            return false;
        }
        shoppingCarRepository.deleteById(itemId);
        return true;
    }

    // Task 004: Obtener carrito activo
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCarModel> getActiveCart(Long cashierId) {
        return shoppingCarRepository.findByCashierIdAndSaleIsNull(cashierId);
    }

    // Task 005: Vaciar carrito
    @Override
    @Transactional
    public void clearCart(Long cashierId) {
        List<ShoppingCarModel> items =
                shoppingCarRepository.findByCashierIdAndSaleIsNull(cashierId);

        shoppingCarRepository.deleteAll(items);
    }

    // Task 006: Obtener carrito por venta
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCarModel> getCartBySale(Long saleId) {
        return shoppingCarRepository.findBySaleId(saleId);
    }

    // Task 007: Asociar carrito a una venta
    @Override
    @Transactional
    public void assignSaleToCart(Long cashierId, Long saleId) {

        // Validamos que la venta exista (usando ISaleRepository)
        if (saleRepository.findById(saleId).isEmpty()) {
            throw new ResourceNotFoundException("Sale not found with ID: " + saleId);
        }

        // Obtenemos los items
        List<ShoppingCarModel> items =
                shoppingCarRepository.findByCashierIdAndSaleIsNull(cashierId);

        // Asignamos el ID de la venta a los Modelos
        for (ShoppingCarModel item : items) {
            item.setSaleId(saleId);
        }

        shoppingCarRepository.saveAll(items);
    }

    @Override
    @Transactional
    public ShoppingCarModel updatePrice(Long itemId, BigDecimal newPrice) {

        ShoppingCarModel item = shoppingCarRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Price must be positive");
        }

        item.setPrice(newPrice);
        item.setSubtotal(newPrice.multiply(BigDecimal.valueOf(item.getQuantity())));

        return shoppingCarRepository.save(item);
    }

    // Task 010: Paginación
    @Override
    @Transactional(readOnly = true)
    public Page<ShoppingCarModel> getItemsBySalePaged(Long saleId, Pageable pageable) {
        // Usamos el metodo específico que creamos en el repositorio para paginación
        return shoppingCarRepository.findBySaleIdPaged(saleId, pageable);
    }
}