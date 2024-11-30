package com.example.zb_weather.service;

import com.example.zb_weather.dto.WeatherDataDto;
import com.example.zb_weather.entity.Weather;
import com.example.zb_weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    @Value("${api.key}")
    private String apiKey;

    private static final Logger logger = Logger.getLogger(WeatherService.class.getName());

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;


    @Transactional
    public WeatherDataDto getWeatherData(LocalDate date) {
        // 날짜로 DB 에서 조회
        Optional<Weather> weather = weatherRepository.findByDate(date);
        if (weather.isPresent()) {
            // 존재한다면 return
            return WeatherDataDto.entityToDto(weather.get());
        }
        Weather newWeather = getWeatherFromAPI();
        // 새로 받아온 데이터를 return
        return WeatherDataDto.entityToDto(newWeather);
    }


    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public Weather getWeatherFromAPI() {
        LocalDate date = LocalDate.now();
        logger.info("API 호출 후 DB 데이터 삽입. 날짜 = " + date);
        // 존재하지 않는다면 API 호출
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=" + apiKey;

        JSONObject response = restTemplate.getForObject(url, JSONObject.class);
        Map<String,Object> tempData = (Map<String, Object>) response.get("main");
        List<JSONObject> weatherData = (List<JSONObject>) response.get("weather");
        Map<String,String> data = (Map<String,String>) weatherData.get(0);

        String main = data.get("main");
        String icon = data.get("icon");
        System.out.println("icon = " + icon);
        double temp = Double.parseDouble(tempData.get("temp").toString()) - 273.15;

        Weather newWeather = Weather.builder()
                .temperature(temp)
                .weather(main)
                .icon(icon)
                .date(date)
                .build();

        weatherRepository.save(newWeather);
        return newWeather;
    }
}
