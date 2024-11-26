package com.example.zb_weather.service;

import com.example.zb_weather.dto.WeatherDataDto;
import com.example.zb_weather.entity.Weather;
import com.example.zb_weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${api.key}")
    private String apiKey;

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    public WeatherDataDto getWeatherData(LocalDate date) {
        // 날짜로 DB 에서 조회
        Optional<Weather> weather = weatherRepository.findByDate(date);
        if (weather.isPresent()) {
            // 존재한다면 return
            return WeatherDataDto.entityToDto(weather.get());
        }
        // 존재하지 않는다면 API 호출
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=" + apiKey;

        JSONObject response = restTemplate.getForObject(url, JSONObject.class);
        JSONArray weatherData = (JSONArray) response.get("weather");
        JSONObject data = (JSONObject) weatherData.get(0);

        String main = data.get("main").toString();
        String icon = data.get("icon").toString();
        double temp = (Double) data.get("temp") - 273.15; // 섭씨로 변환
        LocalDate now = LocalDate.now();

        Weather newWeather = Weather.builder()
                .temperature(temp)
                .weather(main)
                .icon(icon)
                .date(now)
                .build();

        weatherRepository.save(newWeather);
        // 새로 받아온 데이터를 return
        return WeatherDataDto.entityToDto(newWeather);
    }
}
