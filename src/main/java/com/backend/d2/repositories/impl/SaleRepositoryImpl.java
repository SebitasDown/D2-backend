package com.backend.d2.repositories.impl;

import com.backend.d2.models.Sale;
import com.backend.d2.repositories.interfaces.DataSaleRepository;
import com.backend.d2.repositories.interfaces.ISaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SaleRepositoryImpl implements ISaleRepository {

    private final DataSaleRepository jpaRepository;

    @Override
    public Sale save(Sale sale) {
        return jpaRepository.save(sale);
    }

    @Override
    public Optional<Sale> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void delete(Sale sale) {
        jpaRepository.delete(sale);
    }

    @Override
    public List<Sale> searchSalesWithCashier(String searchTerm) {
        return jpaRepository.searchSalesWithCashier(searchTerm);
    }

    @Override
    public List<Sale> findAllSalesWithCashier() {
        return jpaRepository.findAllSalesWithCashier();
    }

    @Override
    public Optional<Sale> findByIdAndCashierId(Long saleId, Long cashierId) {
        return jpaRepository.findByIdAndCashierId(saleId, cashierId);
    }
}