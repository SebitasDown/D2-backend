package com.backend.d2.repositories.interfaces.jpa;

import com.backend.d2.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaCategoryInterface extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {
    boolean existsByName (String name);
}
