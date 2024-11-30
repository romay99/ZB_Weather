package com.example.zb_weather.service;

import com.example.zb_weather.Exception.DiaryNotFoundException;
import com.example.zb_weather.dto.WeatherDataDto;
import com.example.zb_weather.entity.Diary;
import com.example.zb_weather.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final WeatherService weatherService;

    @Transactional
    public Diary postDiary(LocalDate date , String text) {
        WeatherDataDto weatherData = weatherService.getWeatherData(date);
        Diary diary = Diary.builder()
                .weather(weatherData.getWeather())
                .icon(weatherData.getIcon())
                .temperature(weatherData.getTemperature())
                .text(text)
                .date(weatherData.getDate())
                .build();
        return diaryRepository.save(diary);
    }

    @Transactional
    public List<Diary> getDiaryListByDate(LocalDate date) {
        return diaryRepository.findAllByDate(date);

    }

    @Transactional
    public List<Diary> getDiaryListBetweenStartDateEndDate(LocalDate start,LocalDate end) {
        return diaryRepository.findAllByDateBetween(start, end);

    }

    @Transactional
    public Diary updateDiary(LocalDate date, String text) {
        Diary diary = diaryRepository.findTopByDate(date).orElseThrow(
                () -> new DiaryNotFoundException("일기가 존재하지 않습니다.")
        );
        diary.setText(text);
        return diaryRepository.save(diary);
    }

    @Transactional
    public int deleteDiary(LocalDate date) {
        return diaryRepository.deleteAllByDate(date);
    }
}
