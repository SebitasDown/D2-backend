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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Page<SaleModel> searchSalesWithCashier(String searchTerm, Pageable pageable) {
        Specification<SaleEntity> spec = SaleSpecification.searchByTerm(searchTerm);// filtro por ID o nombre de cajero

        // JpaSpecificationExecutor ya tiene un metodo findAll
        return jpaRepository.findAll(spec, pageable)
                .map(saleMapper::toModel);
    }

    @Override
    public Page<SaleModel> findAllSalesWithCashier(Pageable pageable) {
        Specification<SaleEntity> spec = SaleSpecification.joinCashier();

        return jpaRepository.findAll(spec, pageable)
                .map(saleMapper::toModel);
    }

    @Override
    public Optional<SaleModel> findByIdAndCashierId(Long saleId, Long cashierId) {
        return jpaRepository.findByIdAndCashierId(saleId, cashierId)
                .map(saleMapper::toModel);
    }
}