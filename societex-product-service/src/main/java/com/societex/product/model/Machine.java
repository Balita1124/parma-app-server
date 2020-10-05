package com.societex.product.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MACHINES")
@Data
@EqualsAndHashCode(callSuper = true)
public class Machine extends Product {

    private Double width;
    private Double weight;
    private Double length;
    private Double watt;

    public Machine() {
        super();
    }
}
