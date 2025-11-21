package com.backend.d2.services.interfaces;

import com.backend.d2.models.ShoppingCarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IShoppingCarService {

    // Task 001: Agregar item al carrito
    ShoppingCarModel addItem(Long cashierId, Long productId, Integer quantity);

    // Task 002: Actualizar cantidad de un item existente
    ShoppingCarModel updateItem(Long itemId, Integer quantity);

    // Task 003: Eliminar item
    boolean deleteItem(Long itemId);

    // Task 004: Obtener carrito activo (id_sale = null)
    List<ShoppingCarModel> getActiveCart(Long cashierId);

    // Task 005: Vaciar carrito del cajero
    void clearCart(Long cashierId);

    // Task 006: Ver carrito asociado a una venta
    List<ShoppingCarModel> getCartBySale(Long saleId);

    // Task 007: Asociar carrito a una venta (finalizar)
    void assignSaleToCart(Long cashierId, Long saleId);

    // Task 010: Paginación de items por saleId
    Page<ShoppingCarModel> getItemsBySalePaged(Long saleId, Pageable pageable);
}
