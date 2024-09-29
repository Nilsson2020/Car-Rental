package com.example.rental;

import com.example.rental.CarDTO;
import com.example.rental.Entity.Car;
import com.example.rental.Service.CarService;
import com.example.rental.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAvailableCars_returnsListOfCarDTOs() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(1L, "Toyota", 100.0));
        cars.add(new Car(2L, "Honda", 120.0));

        when(carRepository.findAll()).thenReturn(cars);

        List<CarDTO> carDTOs = carService.getAllAvailableCars();

        assertEquals(2, carDTOs.size());
        assertEquals("Toyota", carDTOs.get(0).getName());
    }

    @Test
    void getCarById_returnsCarDTO() {
        Car car = new Car(1L, "Toyota", 100.0);
        when(carRepository.findById(1L)).thenReturn(car);

        CarDTO carDTO = carService.getCarById(1L);

        assertEquals(1L, carDTO.getId());
        assertEquals("Toyota", carDTO.getName());
    }
}
