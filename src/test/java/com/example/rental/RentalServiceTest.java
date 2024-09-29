package com.example.rental;

import com.example.rental.CarDTO;
import com.example.rental.Entity.Rental;
import com.example.rental.RentalResponse;
import com.example.rental.Service.CarService;
import com.example.rental.Service.RentalService;
import com.example.rental.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarService carService;

    @InjectMocks
    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rentCar_successfulRental() {
        Rental rental = new Rental();
        rental.setCarId(1L);
        rental.setFromDate(LocalDate.now().plusDays(1));
        rental.setToDate(LocalDate.now().plusDays(3));
        rental.setDriverAge(25);

        CarDTO carDTO = new CarDTO(1L, "Toyota", 100.0);
        when(carService.getCarById(1L)).thenReturn(carDTO);
        when(rentalRepository.isCarRentedDuringPeriod(1L, rental.getFromDate(), rental.getToDate())).thenReturn(false);

        rentalService.rentCar(rental);

        verify(rentalRepository, times(1)).save(rental);
    }

    @Test
    void getAllRentals_returnsRentalResponseList() {
        List<Rental> rentals = new ArrayList<>();
        Rental rental = new Rental();
        rental.setCarId(1L);
        rental.setDriverName("John Doe");
        rentals.add(rental);

        CarDTO carDTO = new CarDTO(1L, "Toyota", 100.0);

        when(rentalRepository.findAll()).thenReturn(rentals);
        when(carService.getCarById(1L)).thenReturn(carDTO);

        List<RentalResponse> rentalResponses = rentalService.getAllRentals();

        assertEquals(1, rentalResponses.size());
        assertEquals("John Doe", rentalResponses.get(0).getDriverName());
    }
}
