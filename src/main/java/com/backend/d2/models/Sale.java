package com.backend.d2.models;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cashier", referencedColumnName = "id_user")
    //private User cashier;

    @Column(nullable = false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "cash_method", nullable = false)
    private PaymentMethod cashMethod;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    // Relación inversa: una venta tiene muchos items (ShoppingCar)
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    //private List<ShoppingCar> saleItems;

    @Column(name = "is_cancelled", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isCancelled = false;

}
