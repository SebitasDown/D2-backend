package com.backend.d2.repositories.interfaces;

import com.backend.d2.models.Sale;
import java.util.List;
import java.util.Optional;

public interface ISaleRepository {
    Sale save(Sale sale);
    Optional<Sale> findById(Long id);
    void delete(Sale sale);

    List<Sale> searchSalesWithCashier(String searchTerm);
    List<Sale> findAllSalesWithCashier();
    Optional<Sale> findByIdAndCashierId(Long saleId, Long cashierId);
}