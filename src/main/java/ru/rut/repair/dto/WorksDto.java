package ru.rut.repair.dto;

public class WorksDto {

    private Integer id;

    private String name;

    private Integer quantity;

    private Integer actId;

    public WorksDto(Integer id, String name, Integer quantity, Integer actId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.actId = actId;
    }

    public WorksDto(String name, Integer quantity, Integer actId) {
        this.name = name;
        this.quantity = quantity;
        this.actId = actId;
    }

    public WorksDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }
}
