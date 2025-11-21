package com.backend.d2.controllers;

import com.backend.d2.dtos.shoppingCar.requests.AddCartItemRequest;
import com.backend.d2.dtos.shoppingCar.requests.UpdateCartItemPriceRequest;
import com.backend.d2.dtos.shoppingCar.requests.UpdateCartItemQuantityRequest;
import com.backend.d2.dtos.shoppingCar.responses.ShoppingCarResponse;
import com.backend.d2.entity.ShoppingCarEntity;
import com.backend.d2.mappers.ShoppingCarMapper;
import com.backend.d2.models.ShoppingCarModel;
import com.backend.d2.services.interfaces.IShoppingCarService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping-car")
@RequiredArgsConstructor
public class ShoppingCarController {

    private final IShoppingCarService shoppingCarService;
    private final ShoppingCarMapper mapper;

    // ============================================================
    // Task 001: Agregar item al carrito
    // ============================================================
    @PostMapping("/{cashierId}/add")
    public ResponseEntity<ShoppingCarModel> addItem(
            @PathVariable Long cashierId,
            @RequestBody AddCartItemRequest dto) {

        ShoppingCarModel added =
                shoppingCarService.addItem(cashierId, dto.getProductId(), dto.getQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    // ============================================================
    // Task 002: Actualizar cantidad de un item existente
    // ============================================================
    @PatchMapping("/item/{itemId}/quantity")
    public ResponseEntity<ShoppingCarModel> updateQuantity(
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemQuantityRequest dto) {

        ShoppingCarModel updated =
                shoppingCarService.updateItem(itemId, dto.getQuantity());

        return ResponseEntity.ok(updated);
    }

    // ============================================================
    // Task 003: Eliminar item
    // ============================================================
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {

        boolean deleted = shoppingCarService.deleteItem(itemId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // Task 004: Obtener carrito activo
    // ============================================================
    @GetMapping("/{cashierId}/active")
    public ResponseEntity<ShoppingCarResponse> getActiveCart(
            @PathVariable Long cashierId) {

        List<ShoppingCarModel> items = shoppingCarService.getActiveCart(cashierId);

        // Convertir Model → Response usando mapper ya existente
        ShoppingCarResponse response =
                mapper.toShoppingCarResponse(
                        items.stream()
                                .map(mapper::toEntity)
                                .toList()
                );

        return ResponseEntity.ok(response);
    }

    // ============================================================
    // Task 005: Vaciar carrito
    // ============================================================
    @DeleteMapping("/{cashierId}/clear")
    public ResponseEntity<ShoppingCarResponse> clearCart(@PathVariable Long cashierId) {

        shoppingCarService.clearCart(cashierId);

        ShoppingCarResponse response = new ShoppingCarResponse();
        response.setItems(List.of());
        response.setTotal(BigDecimal.ZERO);
        response.setTotalItems(0);
        response.setMessage("Carrito vaciado");

        return ResponseEntity.ok(response);
    }

    // ============================================================
    // Task 006: Obtener carrito por venta ID
    // ============================================================
    @GetMapping("/sale/{saleId}")
    public ResponseEntity<ShoppingCarResponse> getCartBySale(@PathVariable Long saleId) {

        List<ShoppingCarModel> models = shoppingCarService.getCartBySale(saleId);

        ShoppingCarResponse response =
                mapper.toShoppingCarResponse(
                        models.stream()
                                .map(mapper::toEntity)
                                .toList()
                );

        return ResponseEntity.ok(response);
    }

    // ============================================================
    // Task 007: Actualizar precio del item (solo admin)
    // ============================================================
    @PatchMapping("/item/{itemId}/price")
    public ResponseEntity<ShoppingCarModel> updatePrice(
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemPriceRequest dto) {

        ShoppingCarModel updated = shoppingCarService.updatePrice(itemId, dto.getNewPrice());
        return ResponseEntity.ok(updated);
    }

    // ============================================================
    // Task 008: Paginación de items por saleId (Task 010 original)
    // ============================================================
    @GetMapping("/sale/{saleId}/paged")
    public ResponseEntity<Page<ShoppingCarModel>> getItemsBySalePaged(
            @PathVariable Long saleId,
            Pageable pageable) {

        Page<ShoppingCarModel> result = shoppingCarService.getItemsBySalePaged(saleId, pageable);

        return ResponseEntity.ok(result);
    }
}
