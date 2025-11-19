package com.backend.d2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "shopping_car")
public class ShoppingCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shopping_car")
    private Long id;

    // FK a 'sale'
    // ES CLAVE: Si es NULL, significa que el item está "en el carrito" (Task-004)
    // Si tiene valor, significa que ya se vendió
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sale", referencedColumnName = "id_sale")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_product", referencedColumnName = "id_product")
    // private Product product; -> SEBAS <------------------------

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price; // Precio unitario (Task-007 permite editarlo)

    @Column(nullable = false)
    private BigDecimal subtotal; // price * quantity (Task-001 y 002 recalculan esto)

    // FK a User (Cajero)
    // Necesario para Task-004 "Obtener carrito del usuario actual"
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "id_cashier", referencedColumnName = "id_user")
    // private User cashier; -> BANY <------------------------

}
