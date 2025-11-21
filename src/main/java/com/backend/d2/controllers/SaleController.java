package com.backend.d2.controllers;

import com.backend.d2.dtos.sales.requests.ProcessSaleRequest;
import com.backend.d2.dtos.sales.requests.UpdatePaymentMethod;
import com.backend.d2.dtos.sales.responses.ProcessSaleResponse;
import com.backend.d2.dtos.sales.responses.SaleResponse;
import com.backend.d2.dtos.users.responses.UserResponseDTO;
import com.backend.d2.entity.Role;
import com.backend.d2.exceptions.BadRequestException;
import com.backend.d2.models.UserModel;
import com.backend.d2.services.interfaces.ISaleService;
import com.backend.d2.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    // Inyectamos las interfaces
    private final ISaleService saleService;
    private final IUserService userService;

    // --- Simulación de Seguridad (Temporal) ---
    private UserModel getSimulatedUser(Long userId) {
        if (userId == null) {
            // Caso 1: Si no envían header, creamos manualmente un CAJERO dummy
            return new UserModel(1L, "Cajero Test", "cajero@test.com", "pass123*", "000000000", Role.CASHIER);
        }
        if (userId == 99L) {
            // Caso 2: Si envían 99, creamos manualmente un ADMIN dummy
            return new UserModel(99L, "Admin Test", "admin@test.com", "pass123*", "999999999", Role.ADMIN);
        }

        // Caso 3: Usuario real
        // Ya lanza la excepción ResourceNotFoundException si no existe
        UserResponseDTO userDTO = userService.findById(userId);

        // Convertimos el DTO (que devuelve el servicio) al UserModel (que necesita este controlador)
        return new UserModel(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                null, // No tenemos password en el DTO, pero no hace falta para validar roles
                userDTO.getPhone(),
                userDTO.getRole()
        );
    }

    //Task-001: Crear endpoint procesar venta (POST)
    // Accesible para todos los roles
    @PostMapping("/process")
    public ResponseEntity<ProcessSaleResponse> processSale(
            @RequestBody ProcessSaleRequest request,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        // Obtenemos el cajero que está haciendo la venta
        UserModel cashier = getSimulatedUser(userId);

        ProcessSaleResponse response = saleService.processSale(request, cashier.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Task-002: Crear endpoint cancelar venta (PATCH)
    // Accesible para todos los roles
    @PatchMapping("/{saleId}/cancel")
    public ResponseEntity<SaleResponse> cancelSale(
            @PathVariable Long saleId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        UserModel currentUser = getSimulatedUser(userId);
        SaleResponse response = saleService.cancelSale(saleId, currentUser.getId());
        return ResponseEntity.ok(response);
    }

    // Task-003: Crear endpoint actualizar metodo de pago (PATCH)
    // Accesible para todos los roles
    @PatchMapping("/{saleId}/payment-method")
    public ResponseEntity<SaleResponse> updatePaymentMethod(
            @PathVariable Long saleId,
            @RequestBody UpdatePaymentMethod dto,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        UserModel currentUser = getSimulatedUser(userId);
        SaleResponse response = saleService.updatePaymentMethod(saleId, dto, currentUser.getId());
        return ResponseEntity.ok(response);
    }

    // Task-004: Listar ventas con buscador (GET)
    // Ejemplo: /api/v1/sales?searchTerm=juan
    @GetMapping
    public ResponseEntity<Page<SaleResponse>> listSales(
            @RequestParam(required = false) String search,
            @PageableDefault(page = 0, size = 10, sort = {"purchaseDate"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<SaleResponse> sales = saleService.listSales(search, pageable);
        return ResponseEntity.ok(sales);
    }

    //Task-005: Crear endpoint venta por ID (GET)
    // Valida roles (Cashier solo ve las suyas, Admin ve todas)

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleResponse> getSaleById(
            @PathVariable Long saleId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        // Obtenemos el usuario simulado
        UserModel currentUser = getSimulatedUser(userId);

        // ¡La lógica de roles ya está en el servicio!
        SaleResponse sale = saleService.getSaleById(saleId, currentUser);
        return ResponseEntity.ok(sale);
    }

    // Task-006: Crear endpoint para eliminar venta (DELETE) -> solo accesible para ADMIN
    @DeleteMapping("/{saleId}")
    public ResponseEntity<Void> deleteSale(
            @PathVariable Long saleId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        // Simulamos ser ADMIN si nos pasan el ID 99
        UserModel currentUser = getSimulatedUser(userId);

        // La lógica de "solo ADMIN" está en el servicio
        saleService.deleteSale(saleId, currentUser);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Task-007: Crear endpoint actualizar venta (PATCH) -> Admin
    @PatchMapping("/{saleId}")
    public ResponseEntity<SaleResponse> updateSale(
            @PathVariable Long saleId,
            @RequestBody SaleResponse dto, // Admin puede enviar el DTO de respuesta
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        // Simulamos ser ADMIN si nos pasan el ID 99
        UserModel currentUser = getSimulatedUser(userId);

        // La lógica de "solo ADMIN" está en el servicio
        SaleResponse updatedSale = saleService.updateSale(saleId, dto, currentUser);
        return ResponseEntity.ok(updatedSale);
    }
}