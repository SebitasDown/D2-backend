package com.backend.d2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class ShoppingCarModel {

    private Long id;

    private Long productId;

    private Long cashierId;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal subtotal;

    private Long saleId;  // null mientras no se haya procesado la venta

    public ShoppingCarModel() {
    }

    public ShoppingCarModel(Long id, Long productId, Long cashierId, Integer quantity, BigDecimal price, BigDecimal subtotal, Long saleId) {
        this.id = id;
        this.productId = productId;
        this.cashierId = cashierId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.saleId = saleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCashierId() {
        return cashierId;
    }

    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
}
