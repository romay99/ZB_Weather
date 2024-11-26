package com.example.zb_weather.repository;

import com.example.zb_weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Integer> {
    Optional<Weather> findByDate(LocalDate date);
}
