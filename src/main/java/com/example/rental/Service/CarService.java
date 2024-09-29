package com.example.rental.Service;

import com.example.rental.CarDTO;
import com.example.rental.Entity.Car;
import com.example.rental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDTO> getAllAvailableCars() {
        return carRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id);
        return convertToDTO(car);
    }

    private CarDTO convertToDTO(Car car) {
        return new CarDTO(car.getId(), car.getName(), car.getDailyRate());
    }
}
