package ru.rut.repair.dto;

public class InventoryDto {
    private Integer id;

    private Integer number;

    private String inventoryName;

    private String measureUnit;

    private Integer quantityNorm;

    private Integer quantityFact;

    private Integer actId;

    public InventoryDto() {
    }

    /*
    * Конструктор для edit*/
    public InventoryDto(Integer id, Integer number, String inventoryName, String measureUnit, Integer quantityNorm, Integer quantityFact, Integer actId) {
        this.id = id;
        this.number = number;
        this.inventoryName = inventoryName;
        this.measureUnit = measureUnit;
        this.quantityNorm = quantityNorm;
        this.quantityFact = quantityFact;
        this.actId = actId;
    }

    /*
    * Конструктор для создания нового инвентаря*/
    public InventoryDto(Integer number, String inventoryName, String measureUnit, Integer quantityNorm, Integer quantityFact, Integer actId) {
        this.number = number;
        this.inventoryName = inventoryName;
        this.measureUnit = measureUnit;
        this.quantityNorm = quantityNorm;
        this.quantityFact = quantityFact;
        this.actId = actId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }
}
