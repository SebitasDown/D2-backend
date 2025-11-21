package com.backend.d2.repositories.specifications;

import com.backend.d2.entity.CategoryEntity;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<CategoryEntity> nameLike(String name){
        return (root, query,cb) ->{
            if (name == null || name.trim().isEmpty()){
                return null;
            }

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
