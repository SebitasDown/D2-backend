package com.backend.d2.repositories.interfaces;

import com.backend.d2.models.SupplierModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SupplierRepositoryInterface {
    SupplierModel create(SupplierModel supplierModel);
    SupplierModel updated(SupplierModel supplierModel);
    Optional<SupplierModel> findById(Long id);
    void deleteById(Long id);
    List<SupplierModel> findByName(String name);
    Page<SupplierModel> listAll(Pageable pageable);
}
