package ru.rut.repair.dto;

public class ActDto {

    private LocomotiveDto locomotiveDto;
    private Integer id;

    private Integer locomotiveId;

    private String number;

    private String date;

    private String company;

    private String workKind;

    public ActDto() {
    }

    /*
    * Конструктор, если нужно создать новый локомотив */
    public ActDto(LocomotiveDto locomotiveDto, String number, String date, String company, String workKind) {
        this.locomotiveDto = locomotiveDto;
        this.number = number;
        this.date = date;
        this.company = company;
        this.workKind = workKind;
    }

    /*
    * Конструктор, если уже существует локомотив*/
    public ActDto(Integer locomotiveId, String number, String date, String company, String workKind) {
        this.locomotiveId = locomotiveId;
        this.number = number;
        this.date = date;
        this.company = company;
        this.workKind = workKind;
    }

    /*
    * Конструктор для edit, если нужно изменить */
    public ActDto(String number, Integer id, String date, String company, String workKind) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.company = company;
        this.workKind = workKind;
    }

    public LocomotiveDto getLocomotiveDto() {
        return locomotiveDto;
    }

    public void setLocomotiveDto(LocomotiveDto locomotiveDto) {
        this.locomotiveDto = locomotiveDto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocomotiveId() {
        return locomotiveId;
    }

    public void setLocomotiveId(Integer locomotiveId) {
        this.locomotiveId = locomotiveId;
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
}
