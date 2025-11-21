package com.backend.d2.services.interfaces;

import com.backend.d2.models.SupplierModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierServiceInterface {
    SupplierModel create (SupplierModel supplierModel);
    SupplierModel update (SupplierModel supplierModel);
    void delete (Long id);
    List<SupplierModel> searchByName(String name);
    SupplierModel findById(Long id);
    Page<SupplierModel> listAll(Pageable pageable);
}
