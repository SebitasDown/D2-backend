package com.backend.d2.controllers;

import com.backend.d2.dtos.sales.requests.ProcessSaleRequest;
import com.backend.d2.dtos.sales.requests.UpdatePaymentMethod;
import com.backend.d2.dtos.sales.responses.ProcessSaleResponse;
import com.backend.d2.dtos.sales.responses.SaleResponse;
//import com.backend.d2.models.User;
import com.backend.d2.services.interfaces.ISaleService;
//import com.backend.d2.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    // Inyectamos las INTERFACES, no las implementaciones
    private final ISaleService saleService;
//    private final IUserService userService; // Lo usamos para simular el usuario

    // --- Simulación de Seguridad (Temporal) ---
    // Como no tienes Spring Security, simulamos quién es el usuario
    // leyendo un 'header'. En un futuro, esto lo obtendrás del token.
//    private User getSimulatedUser(Long userId) {
//        if (userId == null) {
//            // Si no envían header, simulamos ser el CAJERO 1
//            return userService.getTestUser(1L, User.Role.CASHIER);
//        }
//        if (userId == 99L) {
//            // Si envían 99, simulamos ser ADMIN
//            return userService.getTestUser(99L, User.Role.ADMIN);
//        }
//        // Buscamos al usuario real
//        return userService.findById(userId)
//                .orElseGet(() -> userService.getTestUser(userId, User.Role.CASHIER));
//    }


    /**
     * Task-001: Crear endpoint procesar venta (POST)
     * Accesible para todos los roles
     */
    @PostMapping("/process")
    public ResponseEntity<ProcessSaleResponse> processSale(
            @RequestBody ProcessSaleRequest request,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        // Obtenemos el cajero que está haciendo la venta
//        User cashier = getSimulatedUser(userId);

//        ProcessSaleResponse response = saleService.processSale(request, cashier.getId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return null;
    }

    /**
     * Task-002: Crear endpoint cancelar venta (PATCH)
     * Accesible para todos los roles
     */
    @PatchMapping("/{saleId}/cancel")
    public ResponseEntity<SaleResponse> cancelSale(
            @PathVariable Long saleId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

//        User currentUser = getSimulatedUser(userId);
//        SaleResponse response = saleService.cancelSale(saleId, currentUser.getId());
//        return ResponseEntity.ok(response);
        return null;
    }

    /**
     * Task-003: Crear endpoint actualizar metodo de pago (PATCH)
     * Accesible para todos los roles
     */
    @PatchMapping("/{saleId}/payment-method")
    public ResponseEntity<SaleResponse> updatePaymentMethod(
            @PathVariable Long saleId,
            @RequestBody UpdatePaymentMethod dto,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

//        User currentUser = getSimulatedUser(userId);
//        SaleResponse response = saleService.updatePaymentMethod(saleId, dto, currentUser.getId());
//        return ResponseEntity.ok(response);
        return null;
    }

    /**
     * Task-004: Crear endpoint para listar ventas (GET)
     */
    @GetMapping
    public ResponseEntity<List<SaleResponse>> listSales(
            @RequestParam(required = false) String search) {

        List<SaleResponse> sales = saleService.listSales(search);
        return ResponseEntity.ok(sales);
    }

    /**
     * Task-005: Crear endpoint venta por ID (GET)
     * MANAGER/ADMIN pueden ver cualquier venta
     * CASHIER solo ve las ventas realizadas por él
     */
    @GetMapping("/{saleId}")
    public ResponseEntity<SaleResponse> getSaleById(
            @PathVariable Long saleId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        // Obtenemos el usuario simulado
//        User currentUser = getSimulatedUser(userId);

        // ¡La lógica de roles ya está en el servicio!
//        SaleResponse sale = saleService.getSaleById(saleId, currentUser);
//        return ResponseEntity.ok(sale);
        return null;
    }

    /**
     * Task-006: Crear endpoint para eliminar venta (DELETE)
     * Solo accesible para ADMIN
     */
    @DeleteMapping("/{saleId}")
    public ResponseEntity<Void> deleteSale(
            @PathVariable Long saleId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        // Simulamos ser ADMIN si nos pasan el ID 99
//        User currentUser = getSimulatedUser(userId);

        // La lógica de "solo ADMIN" está en el servicio
//        saleService.deleteSale(saleId, currentUser);
//        return ResponseEntity.noContent().build(); // 204 No Content
        return null;
    }

    /**
     * Task-007: Crear endpoint actualizar venta (PATCH)
     * Solo accesible para ADMIN
     */
    @PatchMapping("/{saleId}")
    public ResponseEntity<SaleResponse> updateSale(
            @PathVariable Long saleId,
            @RequestBody SaleResponse dto, // Admin puede enviar el DTO de respuesta
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        // Simulamos ser ADMIN si nos pasan el ID 99
//        User currentUser = getSimulatedUser(userId);

        // La lógica de "solo ADMIN" está en el servicio
//        SaleResponse updatedSale = saleService.updateSale(saleId, dto, currentUser);
//        return ResponseEntity.ok(updatedSale);

        return null;
    }
}