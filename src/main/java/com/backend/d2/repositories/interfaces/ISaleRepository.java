package com.backend.d2.repositories.interfaces;

import com.backend.d2.models.SaleModel;
import java.util.List;
import java.util.Optional;

public interface ISaleRepository {

    SaleModel save(SaleModel saleModel);

    Optional<SaleModel> findById(Long id);

    void delete(SaleModel saleModel);

    List<SaleModel> searchSalesWithCashier(String searchTerm);

    List<SaleModel> findAllSalesWithCashier();

    Optional<SaleModel> findByIdAndCashierId(Long saleId, Long cashierId);

}