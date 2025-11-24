package com.backend.d2.models;

import java.math.BigDecimal;

public class ProductModel {

    private Long id;

    private CategoryModel category;

    private SupplierModel supplier;

    private String name;

    private String barcode;

    private BigDecimal price;

    private int stock;

    private String description;

    public ProductModel() {
    }

    public ProductModel(Long id, CategoryModel category, SupplierModel supplier, String name, String barcode, BigDecimal price, int stock, String description) {
        this.id = id;
        this.category = category;
        this.supplier = supplier;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public SupplierModel getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierModel supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}