package com.example.zb_weather.controller;

import com.example.zb_weather.entity.Diary;
import com.example.zb_weather.service.DiaryService;
import com.example.zb_weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "일기",description = "일기 관련 API 입니다.")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/create/diary")
    @Operation(summary = "일기 작성하기", description = "일기를 작성하는 API입니다. date 와 text 데이터가 필요합니다.")
    public ResponseEntity<Diary> postDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                           @RequestParam String text) {
        Diary diary = diaryService.postDiary(date, text);
        return ResponseEntity.ok().body(diary);
    }

    @GetMapping("/read/diary")
    @Operation(summary = "일기 조회하기", description = "일기를 조회하는 API 입니다. 조회할 날짜데이터가 필요합니다.")
    public ResponseEntity<List<Diary>> getDiaryList(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok().body(diaryService.getDiaryListByDate(date));
    }

    @GetMapping("/read/diaries")
    @Operation(summary = "일기 조회하기.(날짜구간 설정)", description = "일기를 조회하는 API 입니다.시작날짜와 끝나는 날짜 사이의 일기들을 조회합니다.")
    public ResponseEntity<List<Diary>> getDiaryList(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
            , @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok().body(diaryService.getDiaryListBetweenStartDateEndDate(startDate, endDate));
    }

    @PutMapping("/update/diary")
    @Operation(summary = "일기 수정하기", description = "일기를 수정하는 API입니다. date 와 text 데이터가 필요합니다.")
    public ResponseEntity<Diary> updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                             @RequestParam String text) {
        return ResponseEntity.ok().body(diaryService.updateDiary(date, text));
    }

    @DeleteMapping("/delete/diary")
    @Operation(summary = "일기 삭제하기", description = "일기를 삭제하는 API입니다. 일기를 삭제할 날짜가 필요합니다.")
    public ResponseEntity<String> deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        int i = diaryService.deleteDiary(date);
        return ResponseEntity.ok().body(i + "개의 일기가 삭제되었습니다.");
    }
}
