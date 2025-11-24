package com.backend.d2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class ShoppingCarModel {

    private Long id;

    private Long productId;

    // Campo de "Solo Lectura" para transportar el nombre al Frontend ---
    private String productName;

    private Long cashierId;
    private Long saleId;

//    private ProductModel productId;
//
//    private UserModel cashierId;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal subtotal;

//    private Long saleId;  // null mientras no se haya procesado la venta

    public ShoppingCarModel() {
    }

    public ShoppingCarModel(Long id, Long productId, String productName, Long cashierId, Long saleId, Integer quantity, BigDecimal price, BigDecimal subtotal) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.cashierId = cashierId;
        this.saleId = saleId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCashierId() {
        return cashierId;
    }

    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
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
}
