package com.backend.d2.services.impl;

import com.backend.d2.exceptions.BadRequestException;
import com.backend.d2.models.SupplierModel;
import com.backend.d2.repositories.interfaces.SupplierRepositoryInterface;
import com.backend.d2.services.interfaces.SupplierServiceInterface;
import com.backend.d2.utils.ValidationRegex;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierServiceInterface {


    // Inyeccion de Repository para implementar metodos
    private final SupplierRepositoryInterface supplierRepositoryInterface;


    @Override
    public SupplierModel create(SupplierModel supplierModel) {
        if (supplierModel.getName() == null || supplierModel.getName().trim().isEmpty() || supplierModel.getBrand() == null || supplierModel.getBrand().trim().isEmpty()){
            throw new IllegalArgumentException("No puede estar vacio"); // Aplicar lo de variables y aplicar el global exception
        }
        if (supplierModel.getPhone() == null || supplierModel.getPhone().trim().isEmpty() || supplierModel.getEmail() == null || supplierModel.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("No pude estar vacio");
        }

        if (supplierModel.getEmail().matches(ValidationRegex.EMAIL_REGEX)){
            throw new BadRequestException("Correo invalido");
        }
        return supplierRepositoryInterface.create(supplierModel);

    }

    @Override
    public SupplierModel update(SupplierModel supplierModel) {
        if (supplierModel.getName() == null || supplierModel.getName().trim().isEmpty() || supplierModel.getBrand() == null || supplierModel.getBrand().trim().isEmpty()){
            throw new IllegalArgumentException("No puede estar vacio"); // Aplicar lo de variables y aplicar el global exception
        }
        if (supplierModel.getPhone() == null || supplierModel.getPhone().trim().isEmpty() || supplierModel.getEmail() == null || supplierModel.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("No pude estar vacio");
        }

        SupplierModel exists = supplierRepositoryInterface.findById(supplierModel.getId())
                .orElseThrow(()-> new IllegalArgumentException("No se encontro el Id"));

        exists.setBrand(supplierModel.getBrand());
        exists.setEmail(supplierModel.getEmail());
        exists.setPhone(supplierModel.getPhone());
        exists.setName(supplierModel.getName());

        return supplierRepositoryInterface.updated(exists);
    }

    @Override
    public void delete(Long id) {
        SupplierModel supplierModel = supplierRepositoryInterface.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("El proveedor no existe"));
        supplierRepositoryInterface.deleteById(supplierModel.getId());
    }

    @Override
    public List<SupplierModel> searchByName(String name) {
        return supplierRepositoryInterface.findByName(name);
    }

    @Override
    public SupplierModel findById(Long id) {
        return supplierRepositoryInterface.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    @Override
    public Page<SupplierModel> listAll(Pageable pageable) {
        return supplierRepositoryInterface.listAll(pageable);
    }
}
