package com.backend.d2.repositories.specifications;

import com.backend.d2.dtos.sales.requests.SaleFilterRequest;
import com.backend.d2.entity.SaleEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

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

//
//
//package com.backend.d2.repositories.specifications;
//
//import com.backend.d2.dtos.sales.requests.SaleFilterRequest;
//import com.backend.d2.entity.SaleEntity;
//import jakarta.persistence.criteria.Join;
//import jakarta.persistence.criteria.JoinType;
//import jakarta.persistence.criteria.Predicate;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SaleSpecification {
//
//    // Este metodo reemplaza a tu antiguo 'searchByTerm' y 'joinCashier'
//    public static Specification<SaleEntity> byFilter(SaleFilterRequest filter) {
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            // 1. Filtrar por ID (Exacto)
//            if (filter.getId() != null) {
//                predicates.add(cb.equal(root.get("id"), filter.getId()));
//            }
//
//            // 2. Filtrar por Cajero (Nombre parcial e insensible a mayúsculas)
//            if (filter.getCashierName() != null && !filter.getCashierName().isBlank()) {
//                Join<Object, Object> cashierJoin = root.join("cashier", JoinType.INNER);
//                String term = "%" + filter.getCashierName().toLowerCase() + "%";
//                predicates.add(cb.like(cb.lower(cashierJoin.get("name")), term));
//            }
//
//            // 3. Filtrar por Rango de Totales (Min y Max)
//            if (filter.getMinTotal() != null) {
//                predicates.add(cb.greaterThanOrEqualTo(root.get("total"), filter.getMinTotal()));
//            }
//            if (filter.getMaxTotal() != null) {
//                predicates.add(cb.lessThanOrEqualTo(root.get("total"), filter.getMaxTotal()));
//            }
//
//            // 4. Filtrar por Fechas (Rango)
//            if (filter.getStartDate() != null) {
//                predicates.add(cb.greaterThanOrEqualTo(root.get("purchaseDate"), filter.getStartDate()));
//            }
//            if (filter.getEndDate() != null) {
//                predicates.add(cb.lessThanOrEqualTo(root.get("purchaseDate"), filter.getEndDate()));
//            }
//
//            // OPTIMIZACIÓN: Fetch del cajero para evitar N+1
//            // Esto reemplaza a tu antiguo método 'joinCashier'
//            // Solo se ejecuta si estamos trayendo entidades, no si estamos contando registros (count query)
//            if (Long.class != query.getResultType()) {
//                root.fetch("cashier", JoinType.LEFT);
//            }
//
//            // Combinamos todas las reglas con AND
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }
//}