package ru.rut.repair.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rut.repair.service.ActService;

@Entity
@Table(name = "INVENTORY")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "INVENTORY_NAME")
    private String inventory_name;

    @Column(name = "MEASURE_UNIT")
    private String measure_unit;

    @Column(name = "QUANTITY_NORM")
    private Integer quantity_norm;

    @Column(name = "QUANTITY_FACT")
    private Integer quantity_fact;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACT_ID")
    private Act act;

    public Inventory() {
    }

    public Inventory(Integer number, String inventory_name, String measure_unit, Integer quantity_norm, Integer quantity_fact, Act act) {
        this.number = number;
        this.inventory_name = inventory_name;
        this.measure_unit = measure_unit;
        this.quantity_norm = quantity_norm;
        this.quantity_fact = quantity_fact;
        this.act = act;
    }

    public Inventory(Integer number, String inventory_name, String measure_unit, Integer quantity_norm, Integer quantity_fact) {
        this.number = number;
        this.inventory_name = inventory_name;
        this.measure_unit = measure_unit;
        this.quantity_norm = quantity_norm;
        this.quantity_fact = quantity_fact;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getInventory_name() {
        return inventory_name;
    }

    public void setInventory_name(String inventory_name) {
        this.inventory_name = inventory_name;
    }

    public String getMeasure_unit() {
        return measure_unit;
    }

    public void setMeasure_unit(String measure_unit) {
        this.measure_unit = measure_unit;
    }

    public Integer getQuantity_norm() {
        return quantity_norm;
    }

    public void setQuantity_norm(Integer quantity_norm) {
        this.quantity_norm = quantity_norm;
    }

    public Integer getQuantity_fact() {
        return quantity_fact;
    }

    public void setQuantity_fact(Integer quantity_fact) {
        this.quantity_fact = quantity_fact;
    }

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public void copy(Inventory inventory) {
        number = inventory.getNumber();
        inventory_name = inventory.getInventory_name();
        measure_unit = inventory.getMeasure_unit();
        quantity_norm = inventory.getQuantity_norm();
        quantity_fact = inventory.getQuantity_fact();
    }
}
