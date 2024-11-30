package com.example.zb_weather.service;

import com.example.zb_weather.dto.WeatherDataDto;
import com.example.zb_weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    void getWeatherData() {
        int fisrtDBCount = weatherRepository.findAll().size();
        LocalDate now = LocalDate.now();

        WeatherDataDto weatherData = weatherService.getWeatherData(now);

        assertNotEquals(fisrtDBCount, weatherRepository.findAll().size());
        assertEquals(weatherData.getDate() , now);
    }
}