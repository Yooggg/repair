package ru.rut.repair.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "LOCOMOTIVE")
public class Locomotive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "locomotive")
    private Set<Act> acts;

    @Column(name = "SERIES")
    private String series;

    @Column(name = "FACTORY_NAME")
    private String factoryNumber;

    @Column(name = "SECTION_INDEX")
    private String sectionIndex;

    @Column(name = "HOME_DEPOT")
    private String homeDepot;

    @Column(name = "WORK_FACT")
    private String workFact;

    public Locomotive() {
    }

    public Locomotive(String series, String factoryNumber, String sectionIndex, String homeDepot, String workFact) {
        this.series = series;
        this.factoryNumber = factoryNumber;
        this.sectionIndex = sectionIndex;
        this.homeDepot = homeDepot;
        this.workFact = workFact;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public String getSectionIndex() {
        return sectionIndex;
    }

    public void setSectionIndex(String sectionIndex) {
        this.sectionIndex = sectionIndex;
    }

    public String getHomeDepot() {
        return homeDepot;
    }

    public void setHomeDepot(String homeDepot) {
        this.homeDepot = homeDepot;
    }

    public String getWorkFact() {
        return workFact;
    }

    public void setWorkFact(String workFact) {
        this.workFact = workFact;
    }

    public void copy(Locomotive locomotive){
        series = locomotive.getSeries();
        factoryNumber = locomotive.getFactoryNumber();
        sectionIndex = locomotive.getSectionIndex();
        homeDepot = locomotive.getHomeDepot();
        workFact = locomotive.getWorkFact();
    }
}
