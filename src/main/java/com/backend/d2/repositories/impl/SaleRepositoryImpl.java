package com.backend.d2.repositories.impl;

import com.backend.d2.entity.SaleEntity;
import com.backend.d2.mappers.SaleMapper;
import com.backend.d2.models.SaleModel;
import com.backend.d2.repositories.interfaces.ISaleRepository;
import com.backend.d2.repositories.interfaces.jpa.JpaSaleRepository;
import com.backend.d2.repositories.specifications.SaleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SaleRepositoryImpl implements ISaleRepository {

    private final JpaSaleRepository jpaRepository;
    private final SaleMapper saleMapper;// Este es el traductor Entity <-> Model

    @Override
    public SaleModel save(SaleModel saleModel) {
        SaleEntity entity = saleMapper.toEntity(saleModel);
        SaleEntity savedEntity = jpaRepository.save(entity);
        return saleMapper.toModel(savedEntity);
    }

    @Override
    public Optional<SaleModel> findById(Long id) {
        return jpaRepository.findById(id)
                .map(saleMapper::toModel);
    }

    @Override
    public void delete(SaleModel saleModel) {
        SaleEntity entity = saleMapper.toEntity(saleModel);
        jpaRepository.delete(entity);
    }

    @Override
    public List<SaleModel> searchSalesWithCashier(String searchTerm) {
        Specification<SaleEntity> spec = SaleSpecification.searchByTerm(searchTerm); // filtro por ID o nombre de cajero

        Sort sort = Sort.by(Sort.Direction.DESC, "purchaseDate"); // Definimos el orden -> Ventas más recientes primero

        return jpaRepository.findAll(spec, sort)
                .stream()
                .map(saleMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleModel> findAllSalesWithCashier() {
        Specification<SaleEntity> spec = SaleSpecification.joinCashier();

        Sort sort = Sort.by(Sort.Direction.DESC, "purchaseDate");

        return jpaRepository.findAll(spec, sort)
                .stream()
                .map(saleMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SaleModel> findByIdAndCashierId(Long saleId, Long cashierId) {
        return jpaRepository.findByIdAndCashierId(saleId, cashierId)
                .map(saleMapper::toModel);
    }
}