package com.example.rental.repository;

import com.example.rental.Entity.Rental;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RentalRepository {

    private final JdbcTemplate jdbcTemplate;

    public RentalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isCarRentedDuringPeriod(Long carId, LocalDate fromDate, LocalDate toDate) {
        String sql = "SELECT COUNT(*) FROM rental WHERE car_id = ? AND (from_date <= ? AND to_date >= ?)";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{carId, toDate, fromDate}, Integer.class);
        return count > 0;
    }

    public void save(Rental rental) {
        String sql = "INSERT INTO rental (driver_name, driver_age, car_id, from_date, to_date, revenue) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, rental.getDriverName(), rental.getDriverAge(), rental.getCarId(), rental.getFromDate(), rental.getToDate(), rental.getRevenue());
    }

    public List<Rental> findAll() {
        String sql = "SELECT * FROM rental";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Rental.class));
    }
}
