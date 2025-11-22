package com.backend.d2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_car")
public class ShoppingCarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shopping_car")
    private Long id;

    // FK a la venta (puede ser null hasta finalizar)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sale", referencedColumnName = "id_sale")
    private SaleEntity sale;

    // FK al producto (obligatorio)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
    private ProductEntity product;

    // FK al cajero. Usamos UserEntity.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cashier", referencedColumnName = "id_user", nullable = false)
    private UserEntity cashier;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal subtotal;
}
