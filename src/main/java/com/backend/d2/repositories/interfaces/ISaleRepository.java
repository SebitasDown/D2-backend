package com.backend.d2.repositories.interfaces;

import com.backend.d2.models.SaleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISaleRepository {

    SaleModel save(SaleModel saleModel);

    Optional<SaleModel> findById(Long id);

    void delete(SaleModel saleModel);

    Page<SaleModel> searchSalesWithCashier(String searchTerm, Pageable pageable);

    Page<SaleModel> findAllSalesWithCashier(Pageable pageable);

    Optional<SaleModel> findByIdAndCashierId(Long saleId, Long cashierId);

}