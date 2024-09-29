package com.example.rental.Entity;

public class Car {
    private Long id;
    private String name;
    private double dailyRate;

    public Car() { }

    public Car(Long id, String name, double dailyRate) {
        this.id = id;
        this.name = name;
        this.dailyRate = dailyRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }
}
