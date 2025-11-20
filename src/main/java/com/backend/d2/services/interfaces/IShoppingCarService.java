package com.backend.d2.services.interfaces;

import com.backend.d2.dtos.shoppingCar.responses.ShoppingCarResponse;
import com.backend.d2.models.ShoppingCarModel;

import java.math.BigDecimal;

public interface IShoppingCarService {

    ShoppingCarModel addItem(Long cashierId, Long productId, Integer quantity);

    ShoppingCarModel updateQuantity(Long cashierId, Long itemId, Integer quantity);

    void removeItem(Long cashierId, Long itemId);

    ShoppingCarResponse getActiveCart(Long cashierId);

    ShoppingCarResponse clearCart(Long cashierId);

    ShoppingCarResponse getCartBySaleId(Long saleId);

    ShoppingCarModel updatePrice(Long itemId, BigDecimal newPrice);

    void deleteCartBySaleId(Long saleId);
}
