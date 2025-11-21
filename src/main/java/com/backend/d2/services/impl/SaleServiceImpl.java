package com.backend.d2.services.impl;

import com.backend.d2.dtos.sales.requests.ProcessSaleRequest;
import com.backend.d2.dtos.sales.requests.UpdatePaymentMethod;
import com.backend.d2.dtos.sales.responses.ProcessSaleResponse;
import com.backend.d2.dtos.sales.responses.SaleResponse;
import com.backend.d2.entity.PaymentMethod;
import com.backend.d2.entity.Role;
import com.backend.d2.exceptions.BadRequestException;
import com.backend.d2.exceptions.ResourceNotFoundException;
import com.backend.d2.mappers.SaleMapper;
import com.backend.d2.models.ProductModel;
import com.backend.d2.models.SaleModel;
import com.backend.d2.models.ShoppingCarModel;
import com.backend.d2.models.UserModel;
import com.backend.d2.repositories.interfaces.ISaleRepository;
import com.backend.d2.repositories.interfaces.IShoppingCarRepository;
import com.backend.d2.repositories.interfaces.IUserRepository;
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface;
import com.backend.d2.services.interfaces.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements ISaleService {

    private final ISaleRepository saleRepository;
    private final IShoppingCarRepository shoppingCarRepository;
    private final ProductRepositoryInterface productRepository;
    private final IUserRepository userRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public ProcessSaleResponse processSale(ProcessSaleRequest request, Long cashierId) {

        UserModel cashier = userRepository.findById(cashierId)
                .orElseThrow(() -> new ResourceNotFoundException("Cashier not found with ID: " + cashierId));

        // Task-001: Obtener carrito actual del usuario (usando ShoppingCarModel)
        List<ShoppingCarModel> cartItems = shoppingCarRepository.findByCashierIdAndSaleIsNull(cashierId);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("The cart is empty");
        }

        // Validar stock y calcular total
        BigDecimal total = BigDecimal.ZERO;

        for (ShoppingCarModel item : cartItems) {
            ProductModel product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("product not found with ID: " + item.getProductId()));

            if (product.getStock() < item.getQuantity()) {
                throw new BadRequestException("Insufficient stock for the product: " + product.getName());
            }

            total = total.add(item.getSubtotal());
        }

        // Crear y Guardar la Venta
        SaleModel newSale = new SaleModel();
        newSale.setCashier(cashier);
        newSale.setTotal(total);
        newSale.setCashMethod(request.getCashMethod() != null ? request.getCashMethod() : PaymentMethod.CASH);
        newSale.setPurchaseDate(LocalDate.now());
        newSale.setCancelled(false);

        SaleModel savedSale = saleRepository.save(newSale);

        // Actualizar Stock y Vincular Carrito a la Venta
        for (ShoppingCarModel item : cartItems) {
            ProductModel product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            // Vinculamos el ID de la venta al item
            item.setSaleId(savedSale.getId());
        }

        // Guardamos los cambios en el carrito (ahora vinculados a la venta)
        shoppingCarRepository.saveAll(cartItems);

        // Calcular cambio
        BigDecimal change = BigDecimal.ZERO;
        if (newSale.getCashMethod() == PaymentMethod.CASH) {
            if (request.getAmountPaid() == null || request.getAmountPaid().compareTo(total) < 0) {
                throw new BadRequestException("The amount paid is insufficient");
            }
            change = request.getAmountPaid().subtract(total);
        }

        return new ProcessSaleResponse(
                savedSale.getId(),
                change,
                saleMapper.toSaleResponse(savedSale)
        );
    }

    @Override
    @Transactional
    public SaleResponse cancelSale(Long saleId, Long userId) {

        SaleModel sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        if (!sale.getPurchaseDate().equals(LocalDate.now())) {
            throw new BadRequestException("Only same-day sales can be cancelled");
        }

        if (sale.isCancelled()) {
            throw new BadRequestException("The sale has already been cancelled");
        }

        List<ShoppingCarModel> saleItems = shoppingCarRepository.findBySaleId(saleId);

        // Revertir Stock
        for (ShoppingCarModel item : saleItems) {
            ProductModel product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        sale.setCancelled(true);
        SaleModel updatedSale = saleRepository.save(sale);

        return saleMapper.toSaleResponse(updatedSale);
    }

    @Override
    @Transactional
    public SaleResponse updatePaymentMethod(Long saleId, UpdatePaymentMethod dto, Long userId) {

        SaleModel sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        if (!sale.getPurchaseDate().equals(LocalDate.now())) {
            throw new BadRequestException("You can only update the payment method for same-day sales");
        }

        sale.setCashMethod(dto.getNewPaymentMethod());
        SaleModel updatedSale = saleRepository.save(sale);

        return saleMapper.toSaleResponse(updatedSale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> listSales(String searchTerm) {
        List<SaleModel> sales;
        if (searchTerm == null || searchTerm.isBlank()) {
            sales = saleRepository.findAllSalesWithCashier();
        } else {
            sales = saleRepository.searchSalesWithCashier(searchTerm.trim());
        }
        return saleMapper.toSaleResponseList(sales);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse getSaleById(Long saleId, UserModel currentUser) {
        Optional<SaleModel> saleOpt;

        if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.MANAGER) {
            saleOpt = saleRepository.findById(saleId);
        }
        else if (currentUser.getRole() == Role.CASHIER) {
            saleOpt = saleRepository.findByIdAndCashierId(saleId, currentUser.getId());
        }
        else {
            throw new BadRequestException("Access denied. Role not recognized");
        }

        SaleModel sale = saleOpt
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found or you do not have permission to view it"));

        return saleMapper.toSaleResponse(sale);
    }

    @Override
    @Transactional
    public void deleteSale(Long saleId, UserModel currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new BadRequestException("Access denied. Only ADMIN can delete sales");
        }

        SaleModel sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        List<ShoppingCarModel> saleItems = shoppingCarRepository.findBySaleId(saleId);

        if (!sale.isCancelled()) {
            for (ShoppingCarModel item : saleItems) {
                Optional<ProductModel> productOpt = productRepository.findById(item.getProductId());
                if (productOpt.isPresent()) {
                    ProductModel product = productOpt.get();
                    product.setStock(product.getStock() + item.getQuantity());
                    productRepository.save(product);
                }
            }
        }

        // --- debo asegurarme de que tu repositorio soporte deleteAll con una lista de modelos
        shoppingCarRepository.deleteAll(saleItems);
        saleRepository.delete(sale);
    }

    @Override
    @Transactional
    public SaleResponse updateSale(Long saleId, SaleResponse dto, UserModel currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new BadRequestException("Access denied. Only ADMIN can update sales");
        }

        SaleModel sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        sale.setCashMethod(dto.getCashMethod());
        sale.setTotal(dto.getTotal());
        sale.setCancelled(dto.isCancelled());

        SaleModel updatedSale = saleRepository.save(sale);

        return saleMapper.toSaleResponse(updatedSale);
    }
}