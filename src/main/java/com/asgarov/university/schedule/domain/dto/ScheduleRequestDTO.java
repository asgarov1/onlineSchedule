package com.asgarov.university.schedule.domain.dto;

public class ScheduleRequestDTO {
    private Long id;
    private String role;
    private String dateFrom;
    private String dateTo;

    public ScheduleRequestDTO() {
    }

    public ScheduleRequestDTO(Long id, String role, String dateFrom, String dateTo) {
        this.id = id;
        this.role = role;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
