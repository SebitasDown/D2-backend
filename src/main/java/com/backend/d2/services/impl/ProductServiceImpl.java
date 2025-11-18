package com.backend.d2.services.impl;

import com.backend.d2.models.ProductModel;
import com.backend.d2.services.interfaces.ProductServiceInterface;

public class ProductServiceImpl implements ProductServiceInterface {

    // Inyeccion de RepositoryIMPL

    @Override
    public ProductModel create(ProductModel productModel) {

        //Manejo de errores globales (BusinessException)
        if (productModel.getName() == null || productModel.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Error momentaneo"); // Recordar Poner las excepciones globales
        }


        return null;
    }
}
