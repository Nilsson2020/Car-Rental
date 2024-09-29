package com.example.rental;

import java.time.LocalDate;

public class RentalRequest {
    private String driverName;
    private int driverAge;
    private Long carId;
    private LocalDate pickupDate;
    private LocalDate returnDate;

    public RentalRequest() { }

    public RentalRequest(String driverName, int driverAge, Long carId, LocalDate pickupDate, LocalDate returnDate) {
        this.driverName = driverName;
        this.driverAge = driverAge;
        this.carId = carId;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
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

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
