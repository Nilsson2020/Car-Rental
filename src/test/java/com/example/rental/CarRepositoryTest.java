package com.example.rental;

import com.example.rental.Entity.Car;
import com.example.rental.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CarRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_WithMultipleCars() {
        Car car1 = new Car(1L, "Car A", 50.0);
        Car car2 = new Car(2L, "Car B", 60.0);
        List<Car> mockCars = Arrays.asList(car1, car2);

        when(jdbcTemplate.query(any(String.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(mockCars);

        List<Car> cars = carRepository.findAll();

        assertEquals(2, cars.size());
        assertEquals("Car A", cars.get(0).getName());
        assertEquals(50.0, cars.get(0).getDailyRate());
        assertEquals("Car B", cars.get(1).getName());
        assertEquals(60.0, cars.get(1).getDailyRate());
    }

    @Test
    void testFindAll_WithNoCars() {
        when(jdbcTemplate.query(any(String.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(Arrays.asList());

        List<Car> cars = carRepository.findAll();

        assertTrue(cars.isEmpty());
    }

    @Test
    void testFindById_Found() {
        Long carId = 1L;
        Car mockCar = new Car(carId, "Car A", 50.0);

        when(jdbcTemplate.queryForObject(any(String.class), any(Object[].class), any(BeanPropertyRowMapper.class)))
                .thenReturn(mockCar);

        Car car = carRepository.findById(carId);

        assertNotNull(car);
        assertEquals("Car A", car.getName());
        assertEquals(50.0, car.getDailyRate());
    }

    @Test
    void testFindById_NotFound() {
        Long carId = 1L;

        when(jdbcTemplate.queryForObject(any(String.class), any(Object[].class), any(BeanPropertyRowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1)); // Simulate not found

        Car car = carRepository.findById(carId);

        assertNull(car);
    }
}
