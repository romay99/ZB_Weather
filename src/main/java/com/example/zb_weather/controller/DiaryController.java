package com.example.zb_weather.controller;

import com.example.zb_weather.entity.Diary;
import com.example.zb_weather.service.DiaryService;
import com.example.zb_weather.service.WeatherService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/create/diary")
    public ResponseEntity<Diary> postDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                           @RequestParam String text) {
        Diary diary = diaryService.postDiary(date, text);
        return ResponseEntity.ok().body(diary);
    }

    @GetMapping("/read/diary")
    public ResponseEntity<List<Diary>> getDiaryList(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok().body(diaryService.getDiaryListByDate(date));
    }

    @GetMapping("/read/diaries")
    public ResponseEntity<List<Diary>> getDiaryList(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
            , @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok().body(diaryService.getDiaryListBetweenStartDateEndDate(startDate, endDate));
    }

    @PutMapping("/update/diary")
    public ResponseEntity<Diary> updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                             @RequestParam String text) {
        return ResponseEntity.ok().body(diaryService.updateDiary(date, text));
    }

    @DeleteMapping("/delete/diary")
    public ResponseEntity<String> deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        int i = diaryService.deleteDiary(date);
        return ResponseEntity.ok().body(i + "개의 일기가 삭제되었습니다.");
    }
}
