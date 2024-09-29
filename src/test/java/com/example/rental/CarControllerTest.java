package com.example.rental;

import com.example.rental.CarDTO;
import com.example.rental.Controller.CarController;
import com.example.rental.Service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void getAvailableCars_returnsListOfCarDTOs() {
        List<CarDTO> carDTOList = new ArrayList<>();
        carDTOList.add(new CarDTO(1L, "Toyota", 100.0));
        carDTOList.add(new CarDTO(2L, "Honda", 120.0));

        when(carService.getAllAvailableCars()).thenReturn(carDTOList);

        ResponseEntity<List<CarDTO>> response = carController.getAvailableCars();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Toyota", response.getBody().get(0).getName());
    }

    @Test
    void getCarById_returnsCarDTO() {
        Long carId = 1L;
        CarDTO carDTO = new CarDTO(carId, "Toyota", 100.0);

        when(carService.getCarById(carId)).thenReturn(carDTO);
        ResponseEntity<CarDTO> response = carController.getCarById(carId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(carId, response.getBody().getId());
        assertEquals("Toyota", response.getBody().getName());
    }
}
