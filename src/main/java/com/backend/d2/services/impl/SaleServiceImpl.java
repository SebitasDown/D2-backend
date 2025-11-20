package com.backend.d2.services.impl;

import com.backend.d2.dtos.sales.requests.ProcessSaleRequest;
import com.backend.d2.dtos.sales.requests.UpdatePaymentMethod;
import com.backend.d2.dtos.sales.responses.ProcessSaleResponse;
import com.backend.d2.dtos.sales.responses.SaleResponse;
import com.backend.d2.entity.PaymentMethod;
import com.backend.d2.entity.SaleEntity;
import com.backend.d2.exceptions.BadRequestException;
import com.backend.d2.exceptions.ResourceNotFoundException;
import com.backend.d2.mappers.SaleMapper;
// import com.backend.d2.models.ShoppingCarModel;
import com.backend.d2.models.ProductModel;
import com.backend.d2.models.SaleModel;
//import com.backend.d2.models.ShoppingCar;
// import com.backend.d2.models.UserModel;
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface;
import com.backend.d2.repositories.interfaces.ISaleRepository;
//import com.backend.d2.repositories.interfaces.IShoppingCarRepository;
//import com.backend.d2.repositories.interfaces.IUserRepository;
import com.backend.d2.services.interfaces.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Inyecta todas las dependencias (final)
public class SaleServiceImpl implements ISaleService {

    // Inyección de Dependencias
    private final ISaleRepository saleRepository;
    // private final IShoppingCarRepository shoppingCarRepository;
    private final ProductRepositoryInterface productRepository;
    // private final IUserRepository userRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional // Si algo falla, revierte todos los cambios en la BD
    public ProcessSaleResponse processSale(ProcessSaleRequest request, Long cashierId) {

        // Task-001: Obtener carrito actual del usuario (cashierId)
//        List<ShoppingCar> cartItems = shoppingCarRepository.findByCashierIdAndSaleIsNull(cashierId);
//
//        // Task-001: Validar que el carrito no este vacío
//        if (cartItems.isEmpty()) {
//            throw new BadRequestException("The cart is empty");
//        }

        // Task-001: Validar stock de todos los productos del carrito
//        for (ShoppingCar item : cartItems) {
//            Product product = item.getProduct();
//            if (product.getStock() < item.getQuantity()) {
//                throw new BadRequestException("Insufficient stock for the product: " + product.getName());
//            }
//        }

        // Task-001: Calcular el total del carrito (suma de subtotales)
//        BigDecimal total = cartItems.stream()
//                .map(ShoppingCar::getSubtotal)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Obtener el cajero (Entity)
//        User cashier = userRepository.findById(cashierId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cashier not found"));

        // Task-001: Crear registro en la tabla sale

        // Crear el MODELO de negocio (SaleModel)
//        SaleModel newSale = new SaleModel();

//        newSale.setCashier(cashier);
//        newSale.setTotal(total);
//        newSale.setCashMethod(request.getCashMethod());
//        newSale.setPurchaseDate(LocalDate.now());
//        newSale.setCancelled(false);
//
         // Guarda usando el repositorio que acepta y devuelve los modelos
//        SaleModel savedSale = saleRepository.save(newSale);

        // Task-001: Actualizar Stock y Asociar carrito a venta

        // Como ShoppingCar (Entity) necesita una SaleEntity para la relación,
        // pero nosotros tenemos un SaleModel, creamos una referencia temporal
//        SaleEntity saleReference = new SaleEntity();
//        saleReference.setId(savedSale.getId());

//        for (ShoppingCar item : cartItems) {
//            Product product = item.getProduct();
//            product.setStock(product.getStock() - item.getQuantity());
//            productRepository.save(product); // Guarda el stock actualizado
//            item.setSale(saleReference); // Asocia usando la referencia
//        }
//        shoppingCarRepository.saveAll(cartItems); // Guarda los cambios en el carrito

        // Task-001: Retornar venta creada con numero y cambio
//        BigDecimal change = BigDecimal.ZERO;
//        if (newSale.getCashMethod() == PaymentMethod.CASH) {
//            if (request.getAmountPaid() == null || request.getAmountPaid().compareTo(total) < 0) {
//                // Si el pago es en efectivo y el monto es insuficiente, revertimos todo
//                throw new BadRequestException("The amount paid is insufficient for cash payment");
//            }
//            change = request.getAmountPaid().subtract(total);
//        }

        // Retorna un DTO usando el Mapper (Model -> Response)
        // Usamos el Mapper para convertir la Entidad a un DTO de respuesta
//        return new ProcessSaleResponse(
//                savedSale.getId(),
//                change,
//                saleMapper.toSaleResponse(savedSale) // Usamos el mapper
//        );
        return null;
    }

    @Override
    @Transactional
    public SaleResponse cancelSale(Long saleId, Long userId) {

        // Task-002: Validar que venta existe
        // Buscamos el MODELO
        SaleModel sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        // Task-002: Validar que sea del mismo dia
        if (!sale.getPurchaseDate().equals(LocalDate.now())) {
            throw new BadRequestException("Only same-day sales can be cancelled");
        }

        if (sale.isCancelled()) {
            throw new BadRequestException("The sale has already been cancelled");
        }

        // Task-002: Obtener productos de la venta
//        List<ShoppingCar> saleItems = shoppingCarRepository.findBySaleId(saleId);

        // Task-002: Revertir Stock
//        for (ShoppingCar item : saleItems) {
//            Product product = item.getProduct();
//            product.setStock(product.getStock() + item.getQuantity());
//            productRepository.save(product);
//        }

        // Task-002: Actualizar venta
        sale.setCancelled(true);
        SaleModel updatedSale = saleRepository.save(sale);

        // Task-002: Retornar la venta actualizada (usando el mapper)
        return saleMapper.toSaleResponse(updatedSale);

    }

    @Override
    @Transactional
    public SaleResponse updatePaymentMethod(Long saleId, UpdatePaymentMethod dto, Long userId) {

        // Task-003: Validar que la venta existe
        SaleModel sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        // Task-003: Validar que sea venta del mismo dia
        if (!sale.getPurchaseDate().equals(LocalDate.now())) {
            throw new BadRequestException("You can only update the payment method for same-day sales");
        }

        // Task-003: Actualizar cash_method en sale
        sale.setCashMethod(dto.getNewPaymentMethod());
        SaleModel updatedSale = saleRepository.save(sale);

        // Task-003: Retornar venta actualizada (usando el mapper)
        return saleMapper.toSaleResponse(updatedSale);

    }

    @Override
    @Transactional(readOnly = true) // 'readOnly' optimiza las consultas de solo lectura
    public List<SaleResponse> listSales(String searchTerm) {

        // Task-004: Filtrar Lista de ventas (Buscador) y JOIN con user
        List<SaleModel> sales;
        if (searchTerm == null || searchTerm.isBlank()) {
            sales = saleRepository.findAllSalesWithCashier(); // Query Optimizada
        } else {
            sales = saleRepository.searchSalesWithCashier(searchTerm.trim()); // Query de Búsqueda
        }

        // Task-004: Retornar array (mapeado a DTO)
        return saleMapper.toSaleResponseList(sales);

    }

//    @Override
//    @Transactional(readOnly = true)
//    public SaleResponse getSaleById(Long saleId, User currentUser) {
//        // Task-005: Buscar venta por id
//        Optional<SaleModel> saleOpt;
//
//        // Task-005: MANAGER/ADMIN pueden ver cualquier venta
//        if (currentUser.getRole() == User.Role.ADMIN || currentUser.getRole() == User.Role.MANAGER) {
//            saleOpt = saleRepository.findById(saleId);
//        }
//        // Task-005: CASHIER solo ve las ventas realizadas por el
//        else if (currentUser.getRole() == User.Role.CASHIER) {
//            saleOpt = saleRepository.findByIdAndCashierId(saleId, currentUser.getId());
//        }
//        else {
//            throw new BadRequestException("Access denied. Role not recognized");
//        }
//
//        SaleModel sale = saleOpt
//            .orElseThrow(() -> new ResourceNotFoundException("Sale not found or you do not have permission to view it"));
//
//        return saleMapper.toSaleResponse(sale);
//    }
//
//    @Override
//    @Transactional
//    public void deleteSale(Long saleId, User currentUser) {
//        // Task-006: Validar que solo ADMIN PUEDA EJECUTAR
//        if (currentUser.getRole() != User.Role.ADMIN) {
//            throw new BadRequestException("Access denied. Only ADMIN can delete sales");
//        }
//
//        SaleModel sale = saleRepository.findById(saleId)
    //                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
//
//        // Task-006: Obtener productos de la venta
//        List<ShoppingCar> saleItems = shoppingCarRepository.findBySaleId(saleId);
//
//        // Task-006: Revertir Stock (si la venta no estaba ya cancelada)
//        if (!sale.isCancelled()) {
//            for (ShoppingCar item : saleItems) {
//                Product product = item.getProduct();
//                product.setStock(product.getStock() + item.getQuantity());
//                productRepository.save(product);
//            }
//        }
//
//        shoppingCarRepository.deleteAll(saleItems);
//        saleRepository.delete(sale);
//    }
//
//    @Override
//    @Transactional
//    public SaleResponse updateSale(Long saleId, SaleResponse dto, User currentUser) {
//        // Task-007: Validar que solo ADMIN puede ejecutar
//        if (currentUser.getRole() != User.Role.ADMIN) {
//            throw new BadRequestException("Acceso denegado. Solo ADMIN puede actualizar ventas.");
//        }
//
//        SaleModel sale = saleRepository.findById(saleId)
//                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada."));
//
//        // Task-007: Actualizar campos en sale
//        sale.setCashMethod(dto.getCashMethod());
//        sale.setTotal(dto.getTotal()); // Como pide la Task-007 (Admin puede ajustar)
//        sale.setCancelled(dto.isCancelled());
//
//        SaleModel updatedSale = saleRepository.save(sale);
//
//        // Task-007: Retornar venta actualizada
//        return saleMapper.toSaleResponse(updatedSale);
//    }

}
