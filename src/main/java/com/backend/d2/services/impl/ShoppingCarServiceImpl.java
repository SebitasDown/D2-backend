package com.backend.d2.services.impl;

import com.backend.d2.entity.ProductEntity;
import com.backend.d2.entity.SaleEntity;
import com.backend.d2.entity.ShoppingCarEntity;
import com.backend.d2.mappers.ShoppingCarMapper;
import com.backend.d2.models.ProductModel;
import com.backend.d2.models.ShoppingCarModel;
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface;
import com.backend.d2.repositories.interfaces.jpa.JpaShoppingCarRepository;
import com.backend.d2.repositories.interfaces.jpa.JpaSaleRepository;
import com.backend.d2.services.interfaces.IShoppingCarService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCarServiceImpl implements IShoppingCarService {

    private final JpaShoppingCarRepository shoppingCarRepository;
    private final ProductRepositoryInterface productRepository;
    private final ShoppingCarMapper mapper;
    private final JpaSaleRepository saleRepository;

    // ============================================================
    // Task 001: Agregar item
    // ============================================================
    @Override
    public ShoppingCarModel addItem(Long cashierId, Long productId, Integer quantity) {

        // 1. Validar existencia del producto
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 2. Validar stock disponible
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        // 3. Verificar si ya existe item en carrito activo
        Optional<ShoppingCarEntity> optionalItem =
                shoppingCarRepository.findByCashierIdAndProductIdAndSaleIsNull(cashierId, productId);

        ShoppingCarEntity item;

        if (optionalItem.isPresent()) {
            // Ya existe → incrementar cantidad
            item = optionalItem.get();

            int newQuantity = item.getQuantity() + quantity;

            if (newQuantity > product.getStock()) {
                throw new RuntimeException("Not enough stock to increase quantity");
            }

            item.setQuantity(newQuantity);
            item.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(newQuantity)));

        } else {
            // No existe → crear uno nuevo
            item = new ShoppingCarEntity();
            item.setCashierId(cashierId);

            // Convertir ProductModel → ProductEntity
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(product.getId());
            productEntity.setName(product.getName());
            productEntity.setPrice(product.getPrice());
            productEntity.setStock(product.getStock());
            productEntity.setBarcode(product.getBarcode());
            productEntity.setDescription(product.getDescription());
            // category y supplier si quieres luego los mapeamos

            item.setProduct(productEntity);
            item.setQuantity(quantity);
            item.setPrice(BigDecimal.valueOf(product.getPrice()));
            item.setSubtotal(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
        }

        ShoppingCarEntity saved = shoppingCarRepository.save(item);

        return mapper.toModel(saved);
    }

    // ============================================================
    // Task 002: Actualizar cantidad
    // ============================================================
    @Override
    public ShoppingCarModel updateItem(Long itemId, Integer quantity) {

        ShoppingCarEntity item = shoppingCarRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        ProductModel product = productRepository.findById(item.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (quantity > product.getStock()) {
            throw new RuntimeException("Not enough stock");
        }

        item.setQuantity(quantity);
        item.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(quantity)));

        ShoppingCarEntity saved = shoppingCarRepository.save(item);
        return mapper.toModel(saved);
    }

    // ============================================================
    // Task 003: Eliminar item
    // ============================================================
    @Override
    public boolean deleteItem(Long itemId) {
        if (!shoppingCarRepository.existsById(itemId)) {
            return false;
        }

        shoppingCarRepository.deleteById(itemId);
        return true;
    }

    // ============================================================
    // Task 004: Obtener carrito activo
    // ============================================================
    @Override
    public List<ShoppingCarModel> getActiveCart(Long cashierId) {
        List<ShoppingCarEntity> items =
                shoppingCarRepository.findByCashierIdAndSaleIsNull(cashierId);

        return mapper.toModelList(items);
    }

    // ============================================================
    // Task 005: Vaciar carrito
    // ============================================================
    @Override
    public void clearCart(Long cashierId) {
        List<ShoppingCarEntity> items =
                shoppingCarRepository.findByCashierIdAndSaleIsNull(cashierId);

        shoppingCarRepository.deleteAll(items);
    }

    // ============================================================
    // Task 006: Obtener carrito por venta
    // ============================================================
    @Override
    public List<ShoppingCarModel> getCartBySale(Long saleId) {
        List<ShoppingCarEntity> items = shoppingCarRepository.findBySaleId(saleId);
        return mapper.toModelList(items);
    }

    // ============================================================
    // Task 007: Asociar carrito a una venta
    // ============================================================
    @Override
    public void assignSaleToCart(Long cashierId, Long saleId) {

        SaleEntity sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        List<ShoppingCarEntity> items =
                shoppingCarRepository.findByCashierIdAndSaleIsNull(cashierId);

        for (ShoppingCarEntity item : items) {
            item.setSale(sale);
        }

        shoppingCarRepository.saveAll(items);
    }

    // ============================================================
    // Task 010: Paginación
    // ============================================================
    @Override
    public Page<ShoppingCarModel> getItemsBySalePaged(Long saleId, Pageable pageable) {

        Page<ShoppingCarEntity> page =
                shoppingCarRepository.findBySaleId(saleId, pageable);

        return page.map(mapper::toModel);
    }
}
