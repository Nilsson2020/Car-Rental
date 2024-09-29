package com.example.rental.Service;

import com.example.rental.CarDTO;
import com.example.rental.Entity.Car;
import com.example.rental.Entity.Rental;
import com.example.rental.RentalResponse;
import com.example.rental.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CarService carService;


    public RentalService(RentalRepository rentalRepository, CarService carService) {
        this.rentalRepository = rentalRepository;
        this.carService = carService;
    }

    public void rentCar(Rental rental) {
        if (rental.getFromDate() == null || rental.getToDate() == null) {
            throw new IllegalArgumentException("Pickup and return dates must be specified.");
        }
        if (rental.getFromDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Pickup date cannot be in the past.");
        }
        if (rental.getToDate().isBefore(rental.getFromDate())) {
            throw new IllegalArgumentException("Return date cannot be before the pickup date.");
        }
        if (rental.getDriverAge() < 18) {
            throw new IllegalArgumentException("Driver must be at least 18 years old.");
        }
            // Bonus uppgiften
        if (rentalRepository.isCarRentedDuringPeriod(rental.getCarId(), rental.getFromDate(), rental.getToDate())) {
            throw new IllegalArgumentException("Car is already rented during the requested period.");
        }

        long daysRented = rental.getFromDate().until(rental.getToDate()).getDays();
        if (daysRented <= 0) {
            throw new IllegalArgumentException("Rental period must be at least one day.");
        }

        rental.setRevenue(daysRented * carService.getCarById(rental.getCarId()).getDailyRate());

        rentalRepository.save(rental);
    }

    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll().stream().map(rental -> {
            CarDTO car = carService.getCarById(rental.getCarId());
            return new RentalResponse(
                    rental.getDriverName(),
                    car.getName(),
                    rental.getFromDate(),
                    rental.getToDate(),
                    rental.getRevenue()
            );
        }).collect(Collectors.toList());
    }
}
