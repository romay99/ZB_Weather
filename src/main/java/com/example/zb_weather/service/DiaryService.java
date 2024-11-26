package com.example.zb_weather.service;

import com.example.zb_weather.entity.Diary;
import com.example.zb_weather.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final WeatherService weatherService;

    public void postDiary() {

    }
}
