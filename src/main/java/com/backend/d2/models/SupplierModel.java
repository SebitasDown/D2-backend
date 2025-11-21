package com.backend.d2.models;

public class SupplierModel {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String brand;

    public SupplierModel() {
    }

    public SupplierModel(Long id, String brand, String email, String phone, String name) {
        this.id = id;
        this.brand = brand;
        this.email = email;
        this.phone = phone;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
