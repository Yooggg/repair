package ru.rut.repair.model;

import jakarta.persistence.*;

@Entity
@Table(name = "WORKS")
public class Works {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "WORKS_NAME")
    private String name;

    @Column(name = "QUANTITY")
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACT_ID")
    private Act act;

    public Works(String name, Integer quantity, Act act) {
        this.name = name;
        this.quantity = quantity;
        this.act = act;
    }

    public Works(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Works() {
    }

    public Integer getId() {
        return id;
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

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public void copy(Works works) {
        name = works.getName();
        quantity = works.getQuantity();
    }
}
