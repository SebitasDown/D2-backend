package com.backend.d2.models;



public class ProductModel {

    private Long id;

    private Category category;

    private Supplier supplier;

    private String name;

    private String barcode;

    private double price;

    private int stock;

    private String description;

    public ProductModel() {
    }

    public ProductModel(Long id, Category category, Supplier supplier, String name, String barcode, double price, int stock, String description) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
