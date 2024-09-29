package com.example.rental.Entity;

import java.time.LocalDate;

public class Rental {
    private Long id;
    private String driverName;
    private int driverAge;
    private Long carId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double revenue;

    public Rental() { }

    public Rental(Long id, String driverName, int driverAge, Long carId, LocalDate fromDate, LocalDate toDate, double revenue) {
        this.id = id;
        this.driverName = driverName;
        this.driverAge = driverAge;
        this.carId = carId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(int driverAge) {
        this.driverAge = driverAge;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public boolean isValid() {
        return driverAge >= 18 && fromDate != null && toDate != null && fromDate.isBefore(toDate);
    }
}
