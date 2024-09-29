package com.example.rental;

import com.example.rental.Controller.RentalController;
import com.example.rental.RentalRequest;
import com.example.rental.RentalResponse;
import com.example.rental.Service.RentalService;
import com.example.rental.Entity.Rental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RentalControllerTest {

    @Mock
    private RentalService rentalService;

    @InjectMocks
    private RentalController rentalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rentCar_successful() {
        // Arrange
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setDriverName("John Doe");
        rentalRequest.setDriverAge(25);
        rentalRequest.setCarId(1L);

        doNothing().when(rentalService).rentCar(any(Rental.class));

        // Act
        ResponseEntity<String> response = rentalController.rentCar(rentalRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Car rented successfully.", response.getBody());
    }

    @Test
    void rentCar_badRequest() {
        // Arrange
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setDriverName("John Doe");
        rentalRequest.setDriverAge(25);
        rentalRequest.setCarId(1L);

        doThrow(new IllegalArgumentException("Invalid request")).when(rentalService).rentCar(any(Rental.class));

        ResponseEntity<String> response = rentalController.rentCar(rentalRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid request", response.getBody());
    }

    @Test
    void getAllRentals_returnsRentalResponseList() {
        List<RentalResponse> rentalResponses = new ArrayList<>();
        rentalResponses.add(new RentalResponse("John Doe", "Toyota", null, null, 500.0));

        when(rentalService.getAllRentals()).thenReturn(rentalResponses);

        ResponseEntity<List<RentalResponse>> response = rentalController.getAllRentals();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getDriverName());
    }
}
