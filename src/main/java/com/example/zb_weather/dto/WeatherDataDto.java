package com.example.zb_weather.dto;

import com.example.zb_weather.entity.Weather;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WeatherDataDto {
    private String weather;
    private String icon;
    private double temperature;
    private LocalDate date;

    public static WeatherDataDto entityToDto(Weather weather) {
        WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.weather = weather.getWeather();
        weatherDataDto.icon = weather.getIcon();
        weatherDataDto.temperature = weather.getTemperature();
        weatherDataDto.date = weather.getDate();
        return weatherDataDto;
    }
}
