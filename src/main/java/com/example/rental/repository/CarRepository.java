package com.example.rental.repository;

import com.example.rental.Entity.Car;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {
    private final JdbcTemplate jdbcTemplate;

    public CarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Car> findAll() {
        String sql = "SELECT * FROM car";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Car.class));
    }

    public Car findById(Long id) {
        String sql = "SELECT * FROM car WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Car.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no car is found
        }
    }
}
