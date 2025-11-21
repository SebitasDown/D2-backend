package com.backend.d2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class ShoppingCarModel {

    private Long id;

    private ProductModel productId;

    private UserModel cashierId;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal subtotal;

    private Long saleId;  // null mientras no se haya procesado la venta

    public ShoppingCarModel() {
    }

    public ShoppingCarModel(Long id, ProductModel productId, UserModel cashierId, Integer quantity, BigDecimal subtotal, Long saleId, BigDecimal price) {
        this.id = id;
        this.productId = productId;
        this.cashierId = cashierId;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.saleId = saleId;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductModel getProductId() {
        return productId;
    }

    public void setProductId(ProductModel productId) {
        this.productId = productId;
    }

    public UserModel getCashierId() {
        return cashierId;
    }

    public void setCashierId(UserModel cashierId) {
        this.cashierId = cashierId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
