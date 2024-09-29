package com.example.rental.Controller;

import com.example.rental.Entity.Rental;
import com.example.rental.RentalRequest;
import com.example.rental.RentalResponse;
import com.example.rental.Service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity<String> rentCar(@RequestBody RentalRequest rentalRequest) {
        try {
            Rental rental = new Rental();
            rental.setDriverName(rentalRequest.getDriverName());
            rental.setDriverAge(rentalRequest.getDriverAge());
            rental.setCarId(rentalRequest.getCarId());
            rental.setFromDate(rentalRequest.getPickupDate());
            rental.setToDate(rentalRequest.getReturnDate());

            rentalService.rentCar(rental);
            return ResponseEntity.ok("Car rented successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/overview")
    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        List<RentalResponse> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }
}
