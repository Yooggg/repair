package ru.rut.repair.model;

import jakarta.persistence.*;

@Entity
@Table(name = "INVENTORY")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "INVENTORY_NAME")
    private String inventoryName;

    @Column(name = "MEASURE_UNIT")
    private String measureUnit;

    @Column(name = "QUANTITY_NORM")
    private Integer quantityNorm;

    @Column(name = "QUANTITY_FACT")
    private Integer quantityFact;
    @ManyToOne()
    @JoinColumn(name = "ACT_ID")
    private Act act;

    public Inventory() {
    }

    public Inventory(Integer number, String inventoryName, String measureUnit, Integer quantityNorm, Integer quantityFact, Act act) {
        this.number = number;
        this.inventoryName = inventoryName;
        this.measureUnit = measureUnit;
        this.quantityNorm = quantityNorm;
        this.quantityFact = quantityFact;
        this.act = act;
    }

    public Inventory(Integer number, String inventoryName, String measureUnit, Integer quantityNorm, Integer quantityFact) {
        this.number = number;
        this.inventoryName = inventoryName;
        this.measureUnit = measureUnit;
        this.quantityNorm = quantityNorm;
        this.quantityFact = quantityFact;
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

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Integer getQuantityNorm() {
        return quantityNorm;
    }

    public void setQuantityNorm(Integer quantityNorm) {
        this.quantityNorm = quantityNorm;
    }

    public Integer getQuantityFact() {
        return quantityFact;
    }

    public void setQuantityFact(Integer quantityFact) {
        this.quantityFact = quantityFact;
    }

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public void copy(Inventory inventory) {
        number = inventory.getNumber();
        inventoryName = inventory.getInventoryName();
        measureUnit = inventory.getMeasureUnit();
        quantityNorm = inventory.getQuantityNorm();
        quantityFact = inventory.getQuantityFact();
    }
}
