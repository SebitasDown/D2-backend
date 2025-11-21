package com.backend.d2.repositories.specifications;

import com.backend.d2.entity.SaleEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class SaleSpecification {

    // Metodo estático que construye la lógica de búsqueda
    public static Specification<SaleEntity> searchByTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            // Si no hay término de búsqueda, retornamos "true" -> Va a traer todo
            if (searchTerm == null || searchTerm.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            // Normalizamos a minúsculas para búsqueda insensible a mayúsculas
            String term = "%" + searchTerm.toLowerCase() + "%";

            // Hacemos el JOIN con la tabla de cashier (User) -> Equivalente a: JOIN s.cashier c
            Join<Object, Object> cashierJoin = root.join("cashier", JoinType.INNER);

            // Construimos el OR: -> ID de la venta contiene el término -> Nombre del cajero contiene el término
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("id").as(String.class), term), // Cast ID a String para usar like
                    criteriaBuilder.like(criteriaBuilder.lower(cashierJoin.get("name")), term)
            );
        };
    }

    // parte para optimizar la carga (JOIN FETCH) -> Sirve para traer los datos del cajero en la misma consulta
    public static Specification<SaleEntity> joinCashier() {
        return (root, query, cb) -> {
            // Verifica si ya se ha hecho un fetch para no repetirlo (evita errores en count queries)
            if (Long.class != query.getResultType()) {
                root.fetch("cashier", JoinType.LEFT);
            }
            return cb.conjunction(); // Retorna true (no filtra, solo carga datos)
        };
    }
}