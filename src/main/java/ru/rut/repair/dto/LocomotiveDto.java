package ru.rut.repair.dto;

public class LocomotiveDto {

    private Integer id;

    private String series;

    private  String factoryNumber;

    private String sectionIndex;

    private String homeDepot;

    private String workFact;

    public LocomotiveDto(String series, String factoryNumber, String sectionIndex, String homeDepot, String workFact) {
        this.series = series;
        this.factoryNumber = factoryNumber;
        this.sectionIndex = sectionIndex;
        this.homeDepot = homeDepot;
        this.workFact = workFact;
    }

    public LocomotiveDto() {
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

}
