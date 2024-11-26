package com.example.zb_weather.controller;

import com.example.zb_weather.service.DiaryService;
import com.example.zb_weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final WeatherService weatherService;
    private final DiaryService diaryService;

    @PostMapping("/post")
    public void postDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                          @RequestBody String text) {

    }
}
