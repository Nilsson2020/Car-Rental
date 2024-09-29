package com.example.rental;

import java.time.LocalDate;

public class RentalResponse {
    private String driverName;
    private String carName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double revenue;

    public RentalResponse(String driverName, String carName, LocalDate fromDate, LocalDate toDate, double revenue) {
        this.driverName = driverName;
        this.carName = carName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.revenue = revenue;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
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
}
