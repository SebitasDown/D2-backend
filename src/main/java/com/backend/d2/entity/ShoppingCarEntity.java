package com.backend.d2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "shopping_car")
@Data
@NoArgsConstructor
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

    // ID del cajero (hasta que exista UserEntity)
    @Column(name = "id_cashier", nullable = false)
    private Long cashierId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal subtotal;
}
