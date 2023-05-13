package ru.rut.repair.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ACT")
public class Act {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LOCOMOTIVE_ID")
    private Locomotive locomotive;
    @Column(name = "NUMBER")
    private String number;
    @Column(name = "DATE")
    private String date;
    @Column(name = "COMPANY")
    private String company;
    @Column(name = "KIND_OF_WORK")
    private String workKind;

    public Act(Locomotive locomotive, String number, String date, String company, String workKind) {
        this.locomotive = locomotive;
        this.number = number;
        this.date = date;
        this.company = company;
        this.workKind = workKind;
    }

    public Act(String number, String date, String company, String workKind) {
        this.number = number;
        this.date = date;
        this.company = company;
        this.workKind = workKind;
    }

    public Act() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWorkKind() {
        return workKind;
    }

    public void setWorkKind(String workKind) {
        this.workKind = workKind;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    public void copy(Act act){
        locomotive = act.getLocomotive();
        number = act.getNumber();
        date = act.getDate();
        company = act.getCompany();
        workKind = act.getWorkKind();
    }
}
