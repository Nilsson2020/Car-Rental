package com.example.rental;

import com.example.rental.Entity.Rental;
import com.example.rental.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RentalRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isCarRentedDuringPeriod_returnsTrueWhenCarIsRented() {
        Long carId = 1L;
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusDays(1);

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class)))
                .thenReturn(1);

        boolean result = rentalRepository.isCarRentedDuringPeriod(carId, fromDate, toDate);

        assertTrue(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(Object[].class), eq(Integer.class));
    }

    @Test
    void isCarRentedDuringPeriod_returnsFalseWhenCarIsNotRented() {
        Long carId = 1L;
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusDays(1);

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class)))
                .thenReturn(0);

        boolean result = rentalRepository.isCarRentedDuringPeriod(carId, fromDate, toDate);

        assertFalse(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(Object[].class), eq(Integer.class));
    }

    @Test
    void save_insertsRental() {
        Rental rental = new Rental();
        rental.setDriverName("Ola");
        rental.setDriverAge(30);
        rental.setCarId(1L);
        rental.setFromDate(LocalDate.now());
        rental.setToDate(LocalDate.now().plusDays(1));
        rental.setRevenue(100.0);

        rentalRepository.save(rental);

        verify(jdbcTemplate, times(1)).update(anyString(),
                eq("Ola"), eq(30), eq(1L), eq(rental.getFromDate()), eq(rental.getToDate()), eq(100.0));
    }

    @Test
    void findAll_returnsListOfRentals() {
        Rental rental1 = new Rental();
        rental1.setDriverName("Jakob P");

        Rental rental2 = new Rental();
        rental2.setDriverName("Anders A");

        List<Rental> mockRentals = Arrays.asList(rental1, rental2);

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class)))
                .thenReturn(mockRentals);

        List<Rental> result = rentalRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Jakob P", result.get(0).getDriverName());
        assertEquals("Anders A", result.get(1).getDriverName());
        verify(jdbcTemplate, times(1)).query(anyString(), any(BeanPropertyRowMapper.class));
    }
}
