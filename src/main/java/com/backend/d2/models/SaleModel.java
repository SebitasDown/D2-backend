package com.backend.d2.models;

import com.backend.d2.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SaleModel {

    private Long id;
    private UserModel cashier;
    private BigDecimal total;
    private PaymentMethod cashMethod;
    private LocalDate purchaseDate;
    private List<ShoppingCarModel> saleItems;
    private boolean isCancelled;

    public SaleModel() {
    }

    public SaleModel(Long id, UserModel cashier, BigDecimal total, PaymentMethod cashMethod, LocalDate purchaseDate, List<ShoppingCarModel> saleItems, boolean isCancelled) {
        this.id = id;
        this.cashier = cashier;
        this.total = total;
        this.cashMethod = cashMethod;
        this.purchaseDate = purchaseDate;
        this.saleItems = saleItems;
        this.isCancelled = isCancelled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getCashier() {
        return cashier;
    }

    public void setCashier(UserModel cashier) {
        this.cashier = cashier;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public PaymentMethod getCashMethod() {
        return cashMethod;
    }

    public void setCashMethod(PaymentMethod cashMethod) {
        this.cashMethod = cashMethod;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<ShoppingCarModel> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<ShoppingCarModel> saleItems) {
        this.saleItems = saleItems;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
