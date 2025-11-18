package com.backend.d2.services.interfaces;

import com.backend.d2.dtos.sales.requests.ProcessSaleRequest;
import com.backend.d2.dtos.sales.requests.UpdatePaymentMethod;
import com.backend.d2.dtos.sales.responses.ProcessSaleResponse;
import com.backend.d2.dtos.sales.responses.SaleResponse;

import java.util.List;

// Interfaz para la lógica de negocio de Ventas
public interface ISaleService {

    // Task-001 -> Procesar una nueva venta
    ProcessSaleResponse processSale(ProcessSaleRequest request, Long cashierId);

    // Task-002 -> Cancelar una venta
    SaleResponse cancelSale(Long saleId, Long userId);

    // Task-003 -> Actualizar metodo de pago
    SaleResponse updatePaymentMethod(Long saleId, UpdatePaymentMethod dto, Long userId);

    //Task-004 -> Listar ventas (con filtro)
    List<SaleResponse> listSales(String searchTerm);

    // Task-005: Ver venta por ID (con lógica de roles)
    // SaleResponse getSaleById(Long saleId, User currentUser);

    // Task-006: Eliminar venta (Solo ADMIN)
    // void deleteSale(Long saleId, User currentUser);

    // Task-007: Actualizar campos de una venta (Solo ADMIN)
    // SaleResponse updateSale(Long saleId, SaleResponse dto, User currentUser);

}
