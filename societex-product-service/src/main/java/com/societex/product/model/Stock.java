package com.societex.product.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "STOCKS")
@Data
@EqualsAndHashCode(callSuper = true)
public class Stock extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @NotNull
    private Double quantite;

    private Double quantiteReservee;

    private Integer mini;

    private Integer max;

    private Integer reppro;


}
